package ru.itentica.autoservice.services;



import ru.itentica.autoservice.entities.*;

import java.util.List;

public interface OrderService {
    /**
     * Создание заявки.
     *
     * На вход подаётся логин администратора и объект заявки в следующем виде:
     * {
     * "id": null,
     * "reason": "Требуется ремонт ходовой", // причина обращения в сервис
     * "beginDate": "2022-07-30T12:30", // дата приезда в сервис
     * "endDate": null, // дата завершения ремонта
     * "comment": null, //комментарий к заказу
     * "client": {
     * "id": null,
     * "name": "Евгений", //имя клиента
     * "phoneNumber": "+79150614302",
     * "birthDate": null, //день рождения (опц)
     * "address": null //адресс (опц)
     * },
     * "orderItem": null,
     * "orderHistory": null,
     * "master": null, //мастер принявший работы
     * "administrator": null //администратор принявший работы
     * }
     *
     * Метод возвращает оформленную заявку в статусе NEW со следующими полями:
     * {
     * "id": 26,
     * "reason": "Требуется ремонт ходовой",
     * "beginDate": "2022-07-30T12:30",
     * "endDate": null,
     * "comment": null,
     * "client": {
     * "id": 47,
     * "name": "Евгений",
     * "phoneNumber": "+79150614302",
     * "birthDate": null,
     * "address": null
     * },
     * "orderItem": null,
     * "orderHistory": [
     * {
     * "id": 37,
     * "status": "NEW",
     * "comment": "This is a new order",
     * "createdDate": "2022-07-18T12:30"
     * }
     * ],
     * "master": null,
     * "administrator": { //администратор принявший заказ
     * "id": 7,
     * "name": "Репьева Жанна",
     * "phoneNumber": "1101",
     * "position": { //должность
     * "id": 6,
     * "title": "Администратор"
     * }
     * }
     * }
     * @param login логин администратора
     * @param order частично заполненная заявка
     * @return сформированная заявка в статусе NEW
     */
    Order orderRegistration(String login, Order order, String comment);

    /**
     * Взятие заявку в работу.
     *
     * Администратор назначает мастера в работу, и, заказ автоматически
     * проставляется в соответствующий статус - ACCEPTED:
     * на вход передаем Id заказа и id мастера взявшего заказ в работу, в ответ получаем заказ со
     * следующими объектами:
     * {
     * "id": 26,
     * "reason": "Требуется ремонт ходовой",
     * "beginDate": "2022-07-30T12:30",
     * "endDate": null,
     * "comment": null,
     * "client": {
     * "id": 47,
     * "name": "Евгений",
     * "phoneNumber": "+79150614302",
     * "birthDate": null,
     * "address": null
     * },
     * "orderItem": [],
     * "orderHistory": [
     * {
     * "id": 37,
     * "status": "NEW",
     * "comment": "This is a new order",
     * "createdDate": "2022-07-18T12:30"
     * },
     * {
     * "id": 38,
     * "status": "ACCEPTED", //заказ принят в работу
     * "comment": "Order was accepted",
     * "createdDate": "2022-07-18T12:42"
     * }
     * ],
     * "master": { //назначен мастер
     * "id": 4,
     * "name": "Васюков Роберт",
     * "phoneNumber": "1014",
     * "position": { //должность
     * "id": 5,
     * "title": "Слесарь"
     * }
     * },
     * "administrator": {
     * "id": 7,
     * "name": "Репьева Жанна",
     * "phoneNumber": "1101",
     * "position": {
     * "id": 6,
     * "title": "Администратор"
     * }
     * }
     * }
     * @param orderId идентификатор заявки
     * @param masterId идентификатор мастера
     * @return заявка в статусе ACCEPTED
     */
    Order moveOrderToWork(Long orderId, Long masterId) throws Throwable;

    /**
     * Добавление списка работ в заказ.
     *
     * На вход даем id заказа и список работ:
     * {
     * "items": [
     * {
     * "id": null,
     * "quantity": 2, //количество, зависит от единицы измерения
     * "cost": 600,
     * "priceItem": { //пункт услуги/материала из прайс листа
     * "id": 8,
     * "item": "фильтр салона", //название материала (из прайс листа)
     * "price": 300, //стоимость за единицу из прайс листа
     * "unit": { //ед. измерения из справочника
     * "id": 1,
     * "name": "шт."
     * }
     * }
     * },
     * {
     * "id": null,
     * "quantity": 1,
     * "cost": 300,
     * "priceItem": {
     * "id": 12,
     * "item": "замена фильтра", //название услуги (из прайс листа)
     * "price": 300,
     * "unit": {
     * "id": 3,
     * "name": "чел.ч."
     * }
     * }
     * }
     * ]
     * }
     * на выходе получаем заказ со списком работ:
     * {
     * "id": 26,
     * "reason": "Требуется ремонт ходовой",
     * "beginDate": "2022-07-30T12:30",
     * "endDate": null,
     * "comment": null,
     * "client": {
     * "id": 47,
     * "name": "Евгений",
     * "phoneNumber": "+79150614302",
     * "birthDate": null,
     * "address": null
     * },
     * "orderItem": [ //сохраненный список работ
     * {
     * "id": 50,
     * "quantity": 2,
     * "cost": 600,
     * "priceItem": {
     * "id": 8,
     * "item": "фильтр салона",
     * "price": 300,
     * "unit": {
     * "id": 1,
     * "name": "шт."
     * }
     * }
     * },
     * {
     * "id": 51,
     * "quantity": 1,
     * "cost": 300,
     * "priceItem": {
     * "id": 12,
     * "item": "замена фильтра",
     * "price": 300,
     * "unit": {
     * "id": 3,
     * "name": "чел.ч."
     * }
     * }
     * }
     * ],
     * "orderHistory": [
     * {
     * "id": 37,
     * "status": "NEW",
     * "comment": "This is a new order",
     * "createdDate": "2022-07-18T12:30"
     * },
     * {
     * "id": 39,
     * "status": "ACCEPTED",
     * "comment": "Order was accepted",
     * "createdDate": "2022-07-18T15:36"
     * }
     * ],
     * "master": {
     * "id": 4,
     * "name": "Васюков Роберт",
     * "phoneNumber": "1014",
     * "position": {
     * "id": 5,
     * "title": "Слесарь"
     * }
     * },
     * "administrator": {
     * "id": 7,
     * "name": "Репьева Жанна",
     * "phoneNumber": "1101",
     * "position": {
     * "id": 6,
     * "title": "Администратор"
     * }
     * }
     * }
     * @param orderId идентификатор ордера
     * @param workItems список работ
     * @return ордер с добавленным списком работ
     */
//    Order addWorkItemsToOrder(int orderId, List<WorkItem> workItems);

    /**
     * Обновление статуса заказа.
     *
     * Любым сотрудником статус заказа может быть изменен на следующий из: IN_QUEUE -
     * Заказ в очереди ожидания работ, IN_WORK - Заказ в работе, DONE - Заказ выполнен:
     * на входе передаем id заказа и JSON с информацией по статусу:
     * {
     * "orderId": null,
     * "status": "IN_QUEUE",
     * "comment": "заказ в ожидании работ"
     * }
     * на выходе получаем заказ с обновленной историей статусов:
     * {
     * "id": 26,
     * …
     * "orderHistory": [
     * {
     * "id": 37,
     * "status": "NEW",
     * "comment": "This is a new order",
     * "createdDate": "2022-07-18T12:30"
     * },
     * {
     * "id": 39,
     * "status": "ACCEPTED",
     * "comment": "Order was accepted",
     * "createdDate": "2022-07-18T15:36"
     * },
     * {
     * "id": 41,
     * "status": "IN_QUEUE",
     * "comment": "заказ в ожидании работ",
     * "createdDate": "2022-07-18T15:49"
     * }
     * ],
     * ...
     * }
     * @param orderId идентификатор заявки
     * @param orderStatus новый статус
     * @return обновлённая заявка
     */
    Order updateOrderStatus(Long orderId, OrderStatus orderStatus, String comment) throws Throwable;

    /**
     * Получение текущего статуса заказа.
     *
     * На вход передаем id заказа, на выходе получаем текущий статус (берем самый поздний из
     * истории):
     * {
     * "orderId": 26,
     * "status": "IN_QUEUE",
     * "comment": "заказ в ожидании работ"
     * }
     * @param orderId идентификатор заявки
     * @return текущий статус заявки
     */
    OrderStatus getOrderStatus(Long orderId);

    /**
     * Создание заказа для начала обработки
     */
    Order createInitialOrder(String reason, String comment, Principal client);

    Order getOrder(Long orderId) throws Throwable;

    List<Order> getAllOrders();


    void updateOrder(Order order) throws Exception;
}
