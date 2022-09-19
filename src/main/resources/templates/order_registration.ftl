<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<div id="header">
    <h2>Order creation page</h2>
</div>
<div id="content">
    <#include "menu.ftl">
    <fieldset>
        <legend>Register order</legend>
        <form name="order" action="/register_order" method="post">
            Admin:<br/>
            <!-- обработать пустой список -->
            <select id="admin" name="admin">
                <#list model["adminsList"] as admin>
                    <option value="${admin}">${admin}</option>
                </#list>
            </select><br/>

            Reason:<br/>
            <input type="text" name="reason"/><br/>
            Comment:<br/>
            <input type="text" name="comment"/><br/>
            Client:<br/>
            <!-- обработать пустой список -->
            <select id="client" name="client">
                <#list model["clientsList"] as client>
                    <option value="${client}">${client}</option>
                </#list>
            </select><br/>
            ..or input client name:<br/>
            <input type="text" name="client_name"/><br/>
            <input type="submit" value="Register"/>
        </form>
    </fieldset>
    <br/>

    <table class="datatable">
        <tr>
            <th>ID</th>
            <th>Comment</th>
            <th>Reason</th>
            <th>Begin Date</th>
            <th>End Date</th>
            <th>Client</th>
            <th>Master</th>
            <th>Administrator</th>
        </tr>
        <#list model["orders"] as order>
            <tr>
                <td>${order.id!'Undefined id'}</td>
                <td>${order.comment!'Undefined comment'}</td>
                <td>${order.reason!'Undefined reason'}</td>order_history_items

                <#if (order.beginDate??)>
                    <td>${order.beginDate?string('yyyy-MM-dd')}</td>
                <#else>
                    <td>Undefined begin date</td>
                </#if>

                <#if (order.endDate)??>
                    <td>${order.endDate?string('yyyy-MM-dd')}</td>
                <#else>
                    <td>Undefined end date</td>
                </#if>

                <#if (order.client.name)??>
                    <td>${order.client.name}</td>
                <#else>
                    <td>Undefined client</td>
                </#if>

                <#if (order.master.name)??>
                    <td>${order.master.name}</td>
                <#else>
                    <td>Undefined master</td>
                </#if>

                <#if (order.administrator.name)??>
                    <td>${order.administrator.name}</td>
                <#else>
                    <td>Undefined administrator</td>
                </#if>
            </tr>
        </#list>
    </table>
</div>

</body>
</html>