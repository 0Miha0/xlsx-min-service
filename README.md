# 📊 Excel N-th Smallest Number Finder API

Spring Boot приложение для поиска **N-ого минимального целого числа** из Excel-файла (.xlsx), где данные расположены **в первом столбце**.

---

## 🚀 Возможности

- Принимает путь к локальному `.xlsx` файлу и число `N`
- Извлекает только **целые числа**
- Использует **эффективный алгоритм QuickSelect** (без полной сортировки)
- Документирован через **Swagger UI**

---

## 🧰 Технологии

- Java 17+
- Spring Boot 3.2.6
- Apache POI
- Springdoc OpenAPI (Swagger UI)

---
## 🌐 Swagger UI
Документация и интерфейс доступен после запуска по адресу:
http://localhost:8080/api/v1/swagger-ui/index.html#/Excel%20Number%20Operations

---
## 🔍 Использование API
GET api/v1/numbers/findNthSmallest

---
## ✅ Пример запроса
GET http://localhost:8080/api/v1/numbers/findNthSmallest?filePath=C:/My/textfile.xlsx&number=3

string filePath - путь к Excel-файлу (.xlsx)

int number - номер минимального числа (от 1 и больше)

---
## ⚙️ Установка и запуск
```bash
1. Клонирование проекта
https://github.com/0Miha0/xlsx-min-service.git  

2. Перейдите в директорию проекта
cd xlsx_min_service

2. Сборка проекта
mvn clean package

3. Запуск приложения
java -jar target/xlsx-min-service-0.0.1-SNAPSHOT.jar

