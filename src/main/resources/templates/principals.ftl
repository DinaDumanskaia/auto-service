<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<div id="header">
    <h2>Principal registration page</h2>
</div>
<div id="content">
    <#include "menu.ftl">
    <fieldset>
        <legend>Add Principal</legend>
        <form name="principal" action="/addPrincipal" method="post">
            Name:<br/>
            <input type="text" name="name"/><br/>
            Phone:<br/>
            <input type="text" name="phoneNumber"/><br/>
            Position:<br/>
            <select id="position" name="position">
                <option value="CLIENT">Client</option>
                <option value="ADMINISTRATOR">Administrator</option>
                <option value="SLESAR">Slesar</option>
                <option value="SECRETAR">Secretar</option>
                <option value="STARSHIY_SMENI">Starshiy smeni</option>
                <option value="DIRECTOR">Director</option>
                <option value="MASTER">Master</option>
            </select><br/>
            B.Day:<br/>
            <input type="text" name="birthDate"/><br/>
            Address:<br/>
            <input type="text" name="address"/><br/>
            <input type="submit" value="Add"/>
        </form>
    </fieldset>
    <br/>
    <table class="datatable">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Phone number</th>
            <th>Position</th>
            <th>B.Day</th>
            <th>Address</th>
        </tr>
        <#list model["principalCollection"] as principal>
            <tr>
                <td>${principal.id!'Undefined id'}</td>
                <td>${principal.name!'Undefined name'}</td>
                <td>${principal.phoneNumber!'Undefined phone number'}</td>

                <#if (principal.position.title)??>
                    <td>${principal.position.title}</td>
                <#else>
                    <td>Undefined position</td>
                </#if>

                <#if (principal.birthDate)??>
                    <td>${principal.birthDate?string('yyyy-MM-dd')}</td>
                <#else>
                    <td>Undefined birth date</td>
                </#if>
                <td>${principal.address!'Undefined address'}</td>
            </tr>
        </#list>
    </table>
</div>

</body>
</html>