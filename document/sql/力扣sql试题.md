某网站包含两个表，Customers 表和 Orders 表。编写一个 SQL 查询，找出所有从不订购任何东西的客户。

Customers 表：

+----+-------+
| Id | Name  |
+----+-------+
| 1  | Joe   |
| 2  | Henry |
| 3  | Sam   |
| 4  | Max   |
+----+-------+
Orders 表：

+----+------------+
| Id | CustomerId |
+----+------------+
| 1  | 3          |
| 2  | 1          |
+----+------------+
例如给定上述表格，你的查询应返回：

+-----------+
| Customers |
+-----------+
| Henry     |
| Max       |
+-----------+

# Write your MySQL query statement below
#1计算两个结果集的差集
#别名不同版本可能有问题
# select a.Customers from (select d.Id as Id ,d.name as Customers from Customers d
# union all
# select c.Id as Id,c.name as Customers from Customers c inner join Orders o on c.id = o.CustomerId) a
# group by a.Id
# having count(a.Id) = 1



#2
# select customers.name as 'Customers'
# from customers
# where customers.id not in
# (
#     select customerid from orders
# );

#3
# select name as 'Customers' from customers where not exists (
# 	select customerId from orders where customerId = customers.id
# );


#4
# select name as 'Customers' from customers left join orders
# on customers.id = orders.customerId where isnull(customerId);


