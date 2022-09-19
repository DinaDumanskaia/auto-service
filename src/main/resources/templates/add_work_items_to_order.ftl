<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<div id="header">
    <h2>Assign work items to order</h2>
</div>
<div id="content">
    <#include "menu.ftl">
    <fieldset>
        <legend>Assign work items to order</legend>
        <form name="order" action="/assign_work_item_to_order" method="post">
            Order id:<br/>
            <!-- обработать пустой список -->
            <select id="order_id" name="order_id">
                <#list model["ordersCollection"] as order>
                    <option value="${order.id}">${order.id}</option>
                </#list>
            </select><br/>
            Price item:<br/>
            <select id="price_item_id" name="price_item_id">
                <#list model["priceItems"] as priceItem>
                    <option value="${priceItem.id}">${priceItem.title}</option>
                </#list>
            </select><br/>

            Quantity (1-10):<br/>
            <input type="number" id="quantity" name="quantity" min="1" max="10"><br/>
            <input type="submit" value="Register"/>
        </form>
    </fieldset>
    <br/>

    <table class="datatable">
        <tr>
            <th>Order ID</th>
            <th>Order reason</th>
            <th>Work item details</th>
        </tr>
        <#list model["ordersCollection"] as order>
            <tr>
                <td>${order.id}</td>
                <td>${order.reason!'No reason provided'}</td>

                <#if (order.workItems??)>
                    <td>${order.showAllWorkItems()}</td>
                <#else>
                    <td>No work items has been assigned yet</td>
                </#if>
            </tr>
        </#list>
    </table>
</div>

</body>
</html>