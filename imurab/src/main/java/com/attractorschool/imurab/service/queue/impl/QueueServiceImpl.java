package com.attractorschool.imurab.service.queue.impl;

import com.attractorschool.imurab.dto.queue.*;
import com.attractorschool.imurab.entity.*;
import com.attractorschool.imurab.entity.Queue;
import com.attractorschool.imurab.exception.SeasonException;
import com.attractorschool.imurab.mapper.field.FieldMapper;
import com.attractorschool.imurab.mapper.queue.QueueMapper;
import com.attractorschool.imurab.repository.DepartmentRepository;
import com.attractorschool.imurab.repository.FieldRepository;
import com.attractorschool.imurab.repository.QueueRepository;
import com.attractorschool.imurab.service.field.FieldService;
import com.attractorschool.imurab.service.queue.QueueService;
import com.attractorschool.imurab.util.enums.QStatus;
import com.attractorschool.imurab.util.logicConfig.LogicConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueueServiceImpl implements QueueService {
    private final QueueRepository queueRepository;
    private final FieldRepository fieldRepository;
    private final QueueMapper queueMapper;
    private final FieldService fieldService;
    private final FieldMapper fieldMapper;

    private final DepartmentRepository departmentRepository;

    protected static Map<Long, List<Queue>> ACTUAL_QUEUE = new HashMap<>();

    protected static Map<Long, List<Day>> CURRENT_VACANCIES = new HashMap<>();

    public ResponseEntity<?> createVacant(Long fieldId) {
        try {
            Field field = fieldRepository.findById(fieldId)
                    .orElseThrow(() -> new EntityNotFoundException("Field with ID: " + fieldId + " not found for creating queue."));

            if (field == null) {
                throw new EntityNotFoundException("Field with ID: " + fieldId + " not found for creating queue.");
            }

            dateValidation(field);

            if (isResendQueue(field)) {
                throw new SeasonException("This field is already in the queue or being processed by the operator!");
            }

            Vacant vacant = isFreeVacant((int) fieldService.getIrrigationTime(field), field.getDepartment().getId());
            if (!vacant.isOk()) {
                throw new SeasonException("Sorry, the queue is currently busy. Please try again later.");
            }
            return new ResponseEntity<>(new VacantDto(fieldMapper.convertToFieldDto(field), vacant), HttpStatus.OK);
        } catch (EntityNotFoundException | SeasonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> createQueue(Long fieldId, Vacant vacant) {
        try {
            Field field = fieldRepository.findById(fieldId)
                    .orElseThrow(() -> new EntityNotFoundException("Field with ID: " + fieldId + " not found for creating queue."));
            if (field == null) {
                throw new EntityNotFoundException("Field with ID: " + fieldId + " not found for creating queue.");
            }
            if (isResendQueue(field)) {
                throw new SeasonException("This field is already in the queue or being processed by the operator!");
            }
            if (vacant.getHour() > fieldService.getIrrigationTime(field)) {
                throw new SeasonException("Sorry, watering this field should not exceed " + fieldService.getIrrigationTime(field) + " hours. Please try again later.");
            }
            vacant.setOk(isValidVacant(vacant));
            if (!vacant.isOk()) {
                throw new SeasonException("Sorry, the queue is currently busy. Please try again later.");
            }
            takeQueue(vacant);
            Queue savedQueue = queueRepository.save(new Queue(field, vacant));
            addIntoActualQueue(savedQueue);
            return new ResponseEntity<>(queueMapper.convertToQueueDto(savedQueue), HttpStatus.OK);

        } catch (EntityNotFoundException | SeasonException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private void addIntoActualQueue(Queue queue) {
        if (ACTUAL_QUEUE.isEmpty() || !ACTUAL_QUEUE.containsKey(queue.getDepartment().getId())) {
            ACTUAL_QUEUE.put(queue.getDepartment().getId(), new ArrayList<>());
        }
        ACTUAL_QUEUE.get(queue.getDepartment().getId()).add(queue);
    }

    private boolean isResendQueue(Field field) {
        if (!ACTUAL_QUEUE.isEmpty() && ACTUAL_QUEUE.containsKey(field.getDepartment().getId())) {
            List<Field> fieldsFromQueue = ACTUAL_QUEUE.get(field.getDepartment().getId()).stream().map(Queue::getField).collect(Collectors.toList());
            return fieldsFromQueue.stream().anyMatch(e -> e.getId().equals(field.getId()));
        }
        return false;
    }

    private void dateValidation(Field field) throws SeasonException {
        LocalDate seasonStartDate = field.getDepartment().getAvp().getSeasonStartAt();
        LocalDate seasonEndDate = field.getDepartment().getAvp().getSeasonEndAt();

        if (seasonStartDate == null) {
            throw new SeasonException("The watering season has not yet begun!");
        } else if (LocalDate.now().isBefore(seasonStartDate.minusDays(LogicConfig.registrationStartPeriod))) {
            throw new SeasonException("You cannot apply earlier!");
        } else if (seasonEndDate != null && LocalDate.now().isAfter(seasonEndDate)) {
            throw new SeasonException("The application cannot be made because the irrigation season is already closed!");
        }
    }

    @Override
    public Queue findById(Long id) {
        return queueRepository.findById(id).orElse(null);
    }

    @Override
    public Queue create(Queue queue) {
        return saveOrUpdate(queue);
    }

    @Override
    public Queue saveOrUpdate(Queue queue) {
        return queueRepository.save(queue);
    }

    @Override
    public Queue delete(Queue queue) {
        queueRepository.delete(queue);
        return queue;
    }

    @Override
    public List<Queue> findAll() {
        return queueRepository.findAll();
    }

    @Override
    public Page<Queue> findAll(Pageable pageable) {
        return queueRepository.findAll(pageable);
    }

    @Override
    public Page<Queue> findAllByStatus(QStatus qStatus, Pageable pageable) {
        return queueRepository.findAllByStatus(qStatus, pageable);
    }

    @Override
    public Page<Queue> findAllByDepartmentAvpAndStatus(AVP avp, QStatus qStatus, Pageable pageable) {
        return queueRepository.findAllByDepartmentAvpAndStatus(avp, qStatus, pageable);
    }

    @Override
    public Page<Queue> findAllByDepartmentAndStatus(Department department, QStatus qStatus, Pageable pageable) {
        return queueRepository.findAllByDepartmentAndStatus(department, qStatus, pageable);
    }


    public List<QueueDto> findAllByDepartmentId(Long departmentId) {
        if (!ACTUAL_QUEUE.containsKey(departmentId)) {
            return new ArrayList<>();
        }
        return ACTUAL_QUEUE.get(departmentId).stream()
                .map(queueMapper::convertToQueueDto)
                .collect(Collectors.toList());
    }

    @Bean
    public void setActualQueue() {
        List<Queue> queue = queueRepository.getConfirmedAndCreatedQueue(
                Date.valueOf(LocalDate.now().minusDays(LogicConfig.registrationStartPeriod)));
        ACTUAL_QUEUE = queue.stream().collect(Collectors.groupingBy(e -> e.getDepartment().getId()));

        List<Department> departments = departmentRepository.findAll();
        Map<Long, List<Day>> map = new HashMap<>();
        int daysToAdd = LogicConfig.numberOfDaysInCurrentVacancies;
        for (Department department : departments) {
            List<Day> days = new ArrayList<>();
            for (int i = 0; i < daysToAdd; i++) {
                days.add(new Day(LocalDate.now().minusDays(1).plusDays(i), department.getMaxParallelIrrigation()));
            }
            map.computeIfAbsent(department.getId(), k -> new ArrayList<>()).addAll(days);
        }
        CURRENT_VACANCIES = map;

        for (Queue que : queue) {
            LocalDate startDate = que.getStartTime().toLocalDate();
            int startTime = que.getStartTime().getHour();
            int hours = (int) Duration.between(que.getStartTime(), que.getFinishTime()).toHours();
            Vacant vacant = new Vacant(que.getDepartment().getId(), true, startDate, startTime, hours);
            takeQueue(vacant);
        }
    }

    public Queue confirmUserQueue(Queue queue, User editor) {
        queue.setStatus(QStatus.CONFIRMED);
        queue.setOperator(editor);
        ACTUAL_QUEUE.get(queue.getDepartment().getId())
                .stream().filter(q -> q.getId().equals(queue.getId()))
                .findFirst().get().setStatus(QStatus.CONFIRMED);
        return saveOrUpdate(queue);
    }

    public Queue rejectUserQueue(Queue queue, User editor) {
        queue.setStatus(QStatus.REJECTED);
        queue.setOperator(editor);
        ACTUAL_QUEUE.get(queue.getDepartment().getId())
                .removeIf(q -> queue.getId().equals(q.getId()));
        releaseQueue(queue);
        return saveOrUpdate(queue);
    }

    public Queue skipUserQueue(Queue queue, User editor) {
        queue.setStatus(QStatus.MISSED);
        queue.setOperator(editor);
        ACTUAL_QUEUE.get(queue.getDepartment().getId())
                .removeIf(q -> q.getId().equals(queue.getId()));
        releaseQueue(queue);
        return saveOrUpdate(queue);
    }

    public Queue finishUserQueue(Queue queue) {
        queue.setStatus(QStatus.FINISHED);
        ACTUAL_QUEUE.get(queue.getDepartment().getId())
                .removeIf(q -> q.getId().equals(queue.getId()));
        releaseQueue(queue);
        return saveOrUpdate(queue);
    }

    public List<DayDto> getFreeCurrentVacancies(Long id) {
        if (CURRENT_VACANCIES.containsKey(id))
            return queueMapper.convertToListDto(CURRENT_VACANCIES.get(id));
        else return null;
    }

    private Vacant isFreeVacant(int irrigTime, Long departmentId) {
        List<Day> days = CURRENT_VACANCIES.get(departmentId);
        LocalDate date = null;
        int startHour = 0;
        boolean ok = false;
        int count = 0;
        for (Day day : days) {
            if (day.getDate().isBefore(LocalDate.now()) || day.getDate().equals(LocalDate.now())) {
                continue;
            }
            for (int i = 0; i < 24; i++) {
                if (day.getHours()[i].isFree()) {
                    count++;
                    if (count == 1) {
                        date = day.getDate();
                        startHour = day.getHours()[i].getHour();
                    }
                    if (count == irrigTime) {
                        ok = true;
                        break;
                    }
                    continue;
                }
                count = 0;
                date = null;
            }
            if (ok) {
                break;
            }
        }
        return new Vacant(departmentId, ok, date, startHour, irrigTime);
    }

    private void takeQueue(Vacant vacant) {
        List<Day> days = CURRENT_VACANCIES.get(vacant.getDepartmentId());
        int count = vacant.getHour();
        LocalDate startDate = vacant.getStartDate();
        int startTime = vacant.getStartTime();
        for (Day day : days) {
            if (day.getDate().isAfter(startDate) || day.getDate().equals(startDate)) {
                Hour[] hours = day.getHours();
                int releaseTime = (day.getDate().equals(startDate)) ? startTime : 0;
                for (int j = releaseTime; j < 24 && count > 0; j++) {
                    hours[j].take();
                    count--;
                }
            }
            if (count == 0) {
                break;
            }
        }
    }

    private void releaseQueue(Queue queue) {
        List<Day> days = CURRENT_VACANCIES.get(queue.getDepartment().getId());
        int count = (int) Duration.between(queue.getFinishTime(), queue.getStartTime()).toHours();
        count = Math.abs(count);
        LocalDate startDate = queue.getStartTime().toLocalDate();
        int startTime = queue.getStartTime().getHour();
        for (Day day : days) {
            if (day.getDate().isAfter(startDate) || day.getDate().equals(startDate)) {
                Hour[] hours = day.getHours();
                int releaseTime = (day.getDate().equals(startDate)) ? startTime : 0;
                for (int j = releaseTime; j < 24 && count > 0; j++) {
                    hours[j].release();
                    count--;
                }
            }
            if (count == 0) {
                break;
            }
        }
    }

    @Scheduled(cron = "${cron.expression.hour}")
    public void informationActualisator() {
        System.out.println(LocalDateTime.now() + " clean old and add new Vacancies");
        cleanOldVacancies();
        addNewVacancies();
        cleanQueueList();
    }  // config in application.properties

    private void cleanOldVacancies() {
        for (List<Day> days : CURRENT_VACANCIES.values()) {
            days.removeIf(day -> day.getDate().isBefore(LocalDate.now().minusDays(1)));
        }
    } //everyday

    private void addNewVacancies() {
        for (Map.Entry<Long, List<Day>> entry : CURRENT_VACANCIES.entrySet()) {
            Long key = entry.getKey();
            int maxParallel = departmentRepository.getMaxParallelIrrigationByDepartmentId(key);
            List<Day> days = entry.getValue();

            while (days.size() <= LogicConfig.numberOfDaysInCurrentVacancies) {
                Day lastDay = days.get(days.size() - 1);
                days.add(new Day(lastDay.getDate().plusDays(1),
                        maxParallel));
            }
        }
    }  //everyday

    private void cleanQueueList() {
        for (List<Queue> queues : ACTUAL_QUEUE.values()) {
            queues.stream().filter(queue -> queue.getFinishTime().isBefore(LocalDateTime.now().minusDays(1)))
                    .forEach(queue -> {
                        queue.setStatus(QStatus.FINISHED);
                        saveOrUpdate(queue);
                        queues.remove(queue);
                    });
        }
    }  //everyday

    private boolean isValidVacant(Vacant vacant) {
        List<Day> days = CURRENT_VACANCIES.get(vacant.getDepartmentId());
        LocalDateTime startTime = LocalDateTime.of(vacant.getStartDate(), LocalTime.of(vacant.getStartTime(), 0));
        LocalDateTime endTime = startTime.plusHours(vacant.getHour());
        boolean startedDay = false;
        boolean startedTime = false;
        int count = 0;

        for (Day day : days) {
            if (day.getDate().equals(vacant.getStartDate())) {
                startedDay = true;
            }
            if (startedDay) {
                for (Hour hour : day.getHours()) {
                    LocalDateTime dateTime = LocalDateTime.of(day.getDate(), LocalTime.of(hour.getHour(), 0));
                    if (dateTime.isBefore(startTime) || dateTime.isAfter(endTime)) {
                        continue;
                    }
                    if (hour.getHour() == vacant.getStartTime()) {
                        startedTime = true;
                    }
                    if (startedTime) {
                        if (!hour.isFree()) {
                            return false;
                        }
                        count++;
                        if (count == vacant.getHour()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public List<Queue> findMyActualQueue(Long farmerId) {
        return ACTUAL_QUEUE.values().stream()
                .flatMap(List::stream)
                .filter(queue -> queue.getField().getUser().getId().equals(farmerId))
                .collect(Collectors.toList());
    }

    public Page<Queue> findActual(Pageable pageable){
        return queueRepository.findAllActual(pageable);
    }
}