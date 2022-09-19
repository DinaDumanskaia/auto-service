<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<div id="header">
    <h2>Change order status</h2>
</div>
<div id="content">
    <#include "menu.ftl">
    <fieldset>
        <legend>Change order status</legend>
        <form name="assign_order_status" action="/assign_order_status" method="post">
            Order Id:<br/>
            <!-- обработать пустой список -->
            <select id="order_id" name="order_id">
                <#list model["ordersCollection"] as order>
                    <option value="${order.id}">${order.id}</option>
                </#list>
            </select><br/>

            Status:<br/>
            <select id="status" name="status">
                <option value="NEW">New</option>
                <option value="ACCEPTED">Accepted</option>
                <option value="IN_QUEUE">In queue</option>
                <option value="IN_WORK">In work</option>
                <option value="DONE">Done</option>
            </select><br/>

            Comment:<br/>
            <input type="text" name="comment"/><br/>
            <input type="submit" value="Assign"/>
        </form>
    </fieldset>
    <br/>

    <table class="datatable">
        <tr>
            <th>Order ID</th>
            <th>Worker ID</th>
            <th>Worker name</th>
            <th>Worker Position</th>
            <th>Order Status</th>
            <th>Comment</th>
        </tr>
        <#list model["ordersCollection"] as order>
            <tr>
                <td>${order.id!'Undefined id'}</td>

                <#if (order.master.id)??>
                    <td>${order.master.id}</td>
                <#else>
                    <td>Undefined worker id</td>
                </#if>

                <#if (order.master.name)??>
                    <td>${order.master.name}</td>
                <#else>
                    <td>Undefined worker name</td>
                </#if>

                <#if (order.master.position.title)??>
                    <td>${order.master.position.title}</td>
                <#else>
                    <td>Undefined position</td>
                </#if>

                <#if (order.orderHistory.lastOrderHistoryItem.status)??>
                    <td>${order.orderHistory.lastOrderHistoryItem.status}</td>
                <#else>
                    <td>Undefined status</td>
                </#if>

                <#if (order.orderHistory.lastOrderHistoryItem.comment)??>
                    <td>${order.orderHistory.lastOrderHistoryItem.comment}</td>
                <#else>
                    <td>No comments</td>
                </#if>
            </tr>
        </#list>
    </table>
</div>

</body>
</html>