<#--move to work-->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<div id="header">
    <h2>Add worker</h2>
</div>
<div id="content">
    <#include "menu.ftl">
    <fieldset>
        <legend>Add worker</legend>
        <form name="move_to_work" action="/assign_worker" method="post">
            Order Id:<br/>
            <!-- обработать пустой список -->
            <select id="order_id" name="order_id">
                <#list model["ordersCollection"] as order>
                    <option value="${order.id}">${order.id}</option>
                </#list>
            </select><br/>

            Worker:<br/>
            <!-- обработать пустой список -->
            <select id="worker_id" name="worker_id">
                <#list model["workersList"] as worker>
                    <option value="${worker.id}">${worker.id}  :: ${worker.name}</option>
                </#list>
            </select><br/>

            <input type="submit" value="Set"/>
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
            </tr>
        </#list>
    </table>
</div>

</body>
</html>