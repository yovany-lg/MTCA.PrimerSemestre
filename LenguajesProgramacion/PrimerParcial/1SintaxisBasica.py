#-Sintaxis basica

#--Modos de asignacion

#---Asignacion basica
nombre = "Yovany"
#>>> nombre
#'Yovany'

#---Asignacion por posicion:
#----Se asignan valores en base a la posicion en la tupla
nombre, apellido = "Yovany", "Luis"
#>>> nombre
#'Yovany'
#>>> apellido
#'Luis'

#---Asignacion a traves de una lista:
#----Se asignan valores en base a la posicion en la lista
[nombre, apellido] = ['Yovany', 'Luis']
#>>> nombre, apellido
#('Yovany', 'Luis')

#---Asignacion en secuencia de manera generalizada:
#----Se asignan valores a las variables de manera general,
#----se distribuyen los acacteres de la cadena
a, b, c, d, e, f = 'Yovany'
#>>> a,b
#('Y', 'o')

#*Cuando el numero de variables es menor al de los elementos
#de la derecha se genera un error
#>>> a, b, c, d, e = 'Yovany'
#Traceback (most recent call last):
#  File "<stdin>", line 1, in <module>
#  File "<string>", line 1, in <module>
#ValueError: too many values to unpack (expected 5)

#---Asignacion con emparejamiento:
#----Se asignan valores por parejas
((a, b, c), (d, e, f)) = ('Yov','any')
#>>> a,b,c
#('Y', 'o', 'v')

