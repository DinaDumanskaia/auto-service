<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<div id="header">
    <h2>Get order status</h2>
</div>
<div id="content">
    <#include "menu.ftl">
    <fieldset>
        <legend>Get order status</legend>
        <form name="get_order_status" action="/get_order_current_info" method="post">
            Order Id:<br/>
            <!-- обработать пустой список -->
            <select id="order_id" name="order_id">
                <#list model["ordersCollection"] as order>
                    <option value="${order.id}">${order.id} :: ${order.reason}</option>
                </#list>
            </select><br/>
            <input type="submit" value="Get"/>
        </form>
    </fieldset>
    <br/>

    <#if model["requestedOrder"]??>
        <#assign requestedOrder = model["requestedOrder"]!>

    <form name="update_order" action="/do_update_order" method="post">
        Order id: ${requestedOrder.id!'Undefined id'}<br/>
        <input type="hidden" id="order_id" name="order_id" value=${requestedOrder.id!'Undefined id'}><br/>

        Reason:<br/>
        <#assign reasonValue = requestedOrder.reason!'undefined'>
        <input type="text" name="reason" value="${reasonValue}"/><br/>
        Comment:<br/>
        <#assign commentValue = requestedOrder.comment!'undefined'>
        <input type="text" name="comment" value="${commentValue}"/><br/>

        Begin date:<br/>
        <#if (requestedOrder.beginDate)??>
            <input type="date" name="begin_date" value=${requestedOrder.beginDate?string('yyyy-MM-dd')}>
        <#else>
            <input type="date" name="begin_date">
        </#if>
        <br/>
        End date:<br/>
        <#if (requestedOrder.endDate)??>
            <input type="date" name="end_date" value=${requestedOrder.endDate?string('yyyy-MM-dd')}>
        <#else>
            <input type="date" name="end_date">
        </#if>
        <br/>
        Admin:<br/>
        <!-- обработать пустой список -->
        <select id="admin" name="admin">
            <#list model["adminsList"] as admin>
                <option value="${admin}">${admin}</option>
            </#list>
        </select><br/>

        Client:<br/>
        <!-- обработать пустой список -->
        <select id="client" name="client">
            <#list model["clientsList"] as client>
                <option value="${client}">${client}</option>
            </#list>
        </select><br/>

        Worker:<br/>
        <!-- обработать пустой список -->
        <select id="worker" name="worker">
            <#list model["workersList"] as worker>
                <option value="${worker.name}">${worker.name}</option>
            </#list>
        </select><br/>

        <input type="submit" value="Update"/>
    </form>
    <#else>
        No orders selected.
    </#if>
</div>

</body>
</html>