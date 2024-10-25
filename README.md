# Club-card application. Authentication-service.

## Описание

Система "Клубная карта" представляет собой решение, разработанную для
единой точки регистрации и авторизации, а также управления членством в
клубах и организациях, предоставляющих своим участникам различные
привилегии и сервисы.
Основное назначение системы — обеспечение единой точки регистрации и
аутентификации членов клуба реализованное с помощью 2-ух JWT токенов доступа и продления.

## Установка
1. Склонировать репозиторий.
2. Склонировать ***application.yml***, с профилем *-local*. Итогове название файла: 'application-local.yml'
3. Ввести переменные для подключения к базе данных, порт приложения, секретный ключ, и время жизни JWT токенов доступа и обновления
4. Настроить IDE для запуска на профиле *-local*:
   * Инструкция для Intellij IDEA Ultimate
     * -> **Run/Debug Configuration**
     * -> **More Actions**(⋮)
     * -> **Edit...**
     * -> В поле **Active profiles** вставить *local*
     * Запустить проект кнопкой 'Run'

## Использование
[Спецификация swagger v3](src/main/resources/openapi/auth-service-specification.yml)