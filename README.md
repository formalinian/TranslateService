
## Translate Service

#### Для использования сервиса необходимо завести сервисный аккаунт Yandex и сгенерировать статический Api-Key ([Документация](https://cloud.yandex.ru/docs/translate/))

#### Формат запроса отправляемого сервису:

```
{
  "text": "Hello",
  "sourceLanguageCode": "en", //Может быть пропущен
  "targetLanguageCode": "ru"
}
```

#### Формат получаемого от сервиса сообщения:

```
{
  "text": "Привет"
}
```

#### Формат получаемого от сервиса сообщения если возникает ошибка с Data Base:

```
{
  "code": 500,
  "message": "JdbcSQLSyntaxErrorException"
}
```

#### Формат получаемого от сервиса сообщения если возникает ошибка с Yandex Translate:

```
{
  "code": 400,
  "message": "JdbcSQLSyntaxErrorException"
}
```

#### Сборка Docker Image:

```

```

#### Запуск Docker Image:

```

```
