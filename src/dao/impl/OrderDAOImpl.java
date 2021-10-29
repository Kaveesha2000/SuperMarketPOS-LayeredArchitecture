package dao.impl;

import dao.CrudUtil;
import dao.custom.OrderDAO;
import entity.Customer;
import entity.Orders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean add(Orders orders) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Orders VALUES (?,?,?,?,?)", orders.getOrderId(),
                orders.getOrderDate(), orders.getOrderTime(), orders.getCustId(), orders.getCost());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean update(Orders orders) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public Orders search(String s) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Orders WHERE OrderId=?", s);
        rst.next();
        return new Orders(
                rst.getString("OrderId"),
                LocalDate.parse(rst.getString("OrderDate")),
                LocalTime.parse(rst.getString("OrderTime")),
                rst.getString("CustId"),
                rst.getDouble("Cost")
        );
    }

    @Override
    public ArrayList<Orders> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Orders> allOrders = new ArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Orders");
        while (rst.next()) {
            allOrders.add(new Orders(
                    rst.getString("OrderId"),
                    LocalDate.parse(rst.getString("OrderDate")),
                    LocalTime.parse(rst.getString("OrderTime")),
                    rst.getString("CustId"),
                    rst.getDouble("Cost"))
            );
        }
        return allOrders;
    }

    @Override
    public boolean ifOrderExist(String oid) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT OrderId FROM Orders WHERE OrderId=?", oid);
        return rst.next();
    }

    @Override
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT OrderId FROM Orders ORDER BY OrderId DESC LIMIT 1;");
        return rst.next() ? String.format("OD%03d", (Integer.parseInt(rst.getString("OrderId").replace("OD", "")) + 1)) : "OD001";
    }
}
