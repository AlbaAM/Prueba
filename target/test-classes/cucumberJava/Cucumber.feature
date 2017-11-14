@featureTest
Feature: Uso de cucumber en el que se va a probar el funcionamiento 
de una red social.

@Scenario1
Scenario: Login correcto de un usuario
Given Un usuario y password 
When usuario correcto y password correcta
Then se loguea

@Scenario2
Scenario: Login incorrecto de un usuario
Given Un usuario y password
When Usuario incorrecto y una password incorrecta
Then no se loguea

@Scenario3
Scenario: Registro correcto de un usuario
Given Un nombre, un email, una pwduno y una pwddos
When pwduno es igual a pwddos
Then se registra

@Scenario4
Scenario: Registro incorrecto de un usuario
Given Un nombre, un email, una pwduno y una pwddos
When pwduno es distinto a pwddos
Then no se registra

@Scenario5
Scenario: Registro incorrecto de un usuario
Given Un nombre, un email, una pwduno y una pwddos
When email ya esta en la base de datos
Then no se registra

