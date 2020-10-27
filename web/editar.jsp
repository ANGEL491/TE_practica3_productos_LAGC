<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.emergentes.modelo.Producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Producto producto = (Producto) request.getAttribute("producto");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Nuevo producto</h1>
        <form action="MainController" method="post">
            <table>
                <input type="hidden" name="id" value="${producto.id}">
                <tr>
                    <td>Nombre Producto</td>
                    <td><input type="text" name="nombre_producto" value="${producto.nombre_producto}"></td>
                </tr>
                <tr>
                    <td>Precio</td>
                    <td><input type="text" name="precio" value="${producto.precio}"></td>
                </tr>
                <tr>
                    <td>Cantidad</td>
                    <td><input type="text" name="stock" value="${producto.stock}"></td>
                </tr>
                <tr>
                    
                    <td><input type="submit" value="Enviar"></td>
                </tr>
            </table>
        </form>
    </body>
</html>
