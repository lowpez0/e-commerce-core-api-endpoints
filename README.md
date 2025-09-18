# Spring Boot E-Commerce REST API

Core E-commerce api endpoints 

## Features
- JWT Authentication (Register/Login)
- Role-based Access Control (Customer/Admin/)
- Product Catalog 
- Shopping Cart System
- Order Processing

## Tech Stack
- Java 17 + Spring Boot 3
- Spring Security + JWT
- MySQL

## API Endpoints

### Authentication
| Method | Endpoint                 | Description            
|--------|--------------------------|------------------------
| POST   | `/api/auth/register`     | User registration      
| POST   | `/api/auth/login`        | JWT authentication     
| GET    | `/api/auth/refresh-token`| refresh access-token 

### Products
| Method | Endpoint                           | Description             | Roles         |
|--------|------------------------------------|-------------------------|---------------|
| GET    | `/api/products/category{category}` | Get products by category| Public        |
| GET    | `/api/products`                    | Get all product         | Public        |
| GET    | `/api/products/id/{id}`            | Get product details     | Public        |
| POST   | `/api/products`                    | Create new product      | ADMIN         |
| PUT    | `/api/products/update/{id}`        | Update product          | ADMIN         |
| DELETE | `/api/products/delete/{id}`        | Delete product          | ADMIN         |

### Cart
| Method | Endpoint                    | Description            | Auth Required |
|--------|-----------------------------|------------------------|---------------|
| GET    | `/api/cart`                 | Get user cart          | Yes           |
| POST   | `/api/cart/add`             | Add item to cart       | Yes           |
| PUT    | `/api/cart/update`          | Update item quantity   | Yes           |
| DELETE | `/api/cart/itemId/{itemId}` | Remove item from cart  | Yes           |

### Orders
| Method | Endpoint                      | Description            | Auth Required |
|--------|-------------------------------|------------------------|---------------|
| POST   | `/api/orders/checkout`        | Create order           | Yes           |
| GET    | `/api/orders`                 | Get user orders        | Yes           |
| GET    | `/api/orders/{id}`            | Get order details      | Yes           |
