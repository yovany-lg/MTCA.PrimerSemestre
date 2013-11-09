#-Sintaxis basica

#Impresión de múltiples objetos
x = 'Yovany'
y = 'and'
z = []
z += 'Gaby'
print(x,y,z,sep='_')    #Impresión de múltiples objetos
#>>> print(x,y,z,sep='_')
#Yovany_and_['G', 'a', 'b', 'y']

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

#---Asignacion por series:
#----Se asignan valores en base a la serie generada
a, b, c = range(3)
#>>> a,b
#(0, 1)

#---Otra forma de asignación por secuencias:
#----Se asigna a la variable con el prefijo '*' el resto de la secuencia
seq = [1, 2, 3, 4]
a, *b = seq
#>>> a
#1
#>>> b
#[2, 3, 4]
a, *b = 'spam'
#>>> a,b
#('s', ['p', 'a', 'm'])

#---Asignacion para múltiples destinatarios:
a = b = c = 'spam'
#>>> a,b
#('spam', 'spam')

#---Asignacion para múltiples destinatarios y referencias compartidas:
#----Para objetos inmutables como indices o contadores
a = b = 0
#>>> b = b +1
#>>> a,b
#(0, 1)

#----Para objetos mutables como listas y diccionarios:
#----Cuando este objeto cambia, en las referencias se ve reflejado dicho cambio
a = b = []  #una lista vacía
#>>> b.append(10)
#>>> a,b
#----Se verá reflejado el cambio b.append(10) en la referencia a
#([10], [10])

#---Asignaciones aumentadas:
x = x + y  # Forma Tradicional
x += y  # Nueva Forma Aumentada

#----En números
x=0
x+=1
#>>> x
#1

#----En cadenas
s = "Yovany"
s+="Luis"
#>>> s
#'YovanyLuis'

#----En listas
l = [1,2]
l+=[3]
#>>> l
#[1, 2, 3]

#----En cadenas
s = "Yovany"
s+="Luis"
#>>> s
#'YovanyLuis'

#----En listas y cadenas el comportamiento es diferente
#----Se genera una lista con los caracteres de la cadena
l = []
l += "yovany"
#>>> l
#['y', 'o', 'v', 'a', 'n', 'y']
#----Pero al hacer la concatenación normal, se produce un error
l = l+ "luis"
#Traceback (most recent call last):
#  File "<stdin>", line 1, in <module>
#TypeError: can only concatenate list (not "str") to list

#----Asignación aumentada y referencias compartidas
l = [1,2]
m = l
l = l + [3,4]   #Crea un nuevo objeto, ya que se realiza la concatenacion normal
#>>> l,m
#([1, 2, 3, 4], [1, 2])
#----Pero al hacer +=
l = [1,2]
m = l
l += [3,4]   #El cambio se ve reflejado a ambas referencias
#>>> l,m
#([1, 2, 3, 4], [1, 2, 3, 4])

#---- += se usa para cambios directos de listas, pero + como concatenación genera un nuevo obtejo
