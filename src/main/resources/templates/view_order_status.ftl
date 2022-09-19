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
        <form name="get_order_status" action="/get_order_status" method="post">
            Order Id:<br/>
            <!-- обработать пустой список -->
            <select id="order_id" name="order_id">
                <#list model["ordersCollection"] as order>
                    <option value="${order.id}">${order.id}</option>
                </#list>
            </select><br/>
            <input type="submit" value="Get"/>
        </form>
    </fieldset>
    <br/>

    <#if model["requestedOrder"]??>
        <#assign requestedOrder = model["requestedOrder"]!>
        <table class="datatable">
            <tr>
                <th>Order ID</th>
                <th>Order Status</th>
<#--                <th>Comment</th>-->
            </tr>

            <tr>
                <td>${requestedOrder.id!'Undefined id'}</td>

                <#if (requestedOrder.lastOrderHistoryItem.status)??>
                    <td>${requestedOrder.lastOrderHistoryItem.status}</td>
                <#else>
                    <td>Undefined status</td>
                </#if>

<#--                <#if (requestedOrder.orderHistory.lastOrderHistoryItem.comment)??>-->
<#--                    <td>${requestedOrder.orderHistory.lastOrderHistoryItem.comment}</td>-->
<#--                <#else>-->
<#--                    <td>No comments</td>-->
<#--                </#if>-->
            </tr>

        </table>
    <#else>
        No orders selected.
    </#if>
</div>

</body>
</html>