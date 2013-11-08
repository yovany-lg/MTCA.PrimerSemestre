#- Números

#--Enteros
100 + 25
#>>> 100 + 25
#125
#--Punto flotante
2.5 * 100
#>>> 2.5 * 100
#250.0
#--Elevar un número a la potencia
2**10
#>>> 2**10
#1024

#- Cadenas

a = 'Gaby'

#--Longitud de cadena
len(a)

#--Obtener elementos según posición
a[0]
a[1]
#>>> a[0]
#'G'
#>>> a[1]
#'a'
a[-1]   #Último elemento desde el fin de la cadena
a[-2]   #El penúltimo elemento de la cadena
#>>> a[-1]
#'y'
#>>> a[-2]
#'b'

#--Subcadenas de la cadena original
a[1:3]
#>>> a[1:3]
#'ab'

#--Concatenación
a + 'kam'
#>>> a + 'kam'
#'Gabykam'

#--Repeticiones
a*5 #Repetir n veces la cadena original
#>>> a*5
#'GabyGabyGabyGabyGaby'

#--Inmutabilidad
a[0] = 'g'  #Al ser objetos inmutables no pueden ser cambiados
#>>> a[0] = 'g'
#Traceback (most recent call last):
#  File "<stdin>", line 1, in <module>
#TypeError: 'str' object does not support item assignment
a = 'g' + a[1:] #Pero se pueden crear nuevos objetos por medio de operaciones
#>>> a = 'g' + a[1:]
#>>> a
#'gaby'

#--Expansión en listas
a = 'Gotita'
li = list(a)
#>>> a = 'Gotita'
#li = list(a)
#>>> >>> li
#['G', 'o', 't', 'i', 't', 'a']
li[0] = 'g' #Cambiar un elemento de la lista
'.'.join(li) #Unir la lista en una cadena con un separador
#>>> li[0] = 'g'
#>>> '.'.join(li)
#'g.o.t.i.t.a'

#--Encontrar posición de una subcadena en una cadena
a.find('ti')
#>>> a.find('ti')
#2

#--División de una cadena mediante un delimitador
li2 = '.'.join(li)  #Unión de los elementos de la lista con un '.' como separador
li2.split('.')  #División mediante el separador
#>>> li2 = '.'.join(li)
#>>> li2
#'g.o.t.i.t.a'
#>>> li2.split('.')
#['g', 'o', 't', 'i', 't', 'a']

#--Texto con formato
'%s -- %s' % ('Gaby', 'Kam')
#>>> '%s -- %s' % ('Gaby', 'Kam')
#'Gaby -- Kam'
'{:,.2f}'.format(296999.2567)   #Manejo de separadores (',') y máximo dígitos en punto flotante ('.2f')
#>>> '{:,.2f}'.format(296999.2567)
#'296,999.26'

#-Listas

li = [123, 'hola', 1.23] #Lista con diferentes tipos de elementos
#>>> li
#[123, 'hola', 1.23]

len(li) #Longitud de la lista
#>>> len(li)
#3

li[0]   #Elemento de la lista según posición
#>>> li[0]
#123

li[1:]  #Subcadena de la lista según la posición
#>>> li[1:]
#['hola', 1.23]

li + [4, 5, 6]  #Concatenación
#>>> li + [4, 5, 6]
#[123, 'hola', 1.23, 4, 5, 6]

li.append('HOLA!!') #Agregar un elemento al final de la lista
#>>> li.append('HOLA!!')
#>>> li
#[123, 'hola', 1.23, 'HOLA!!']

#--Ordenación de los elementos
li = ['Gaby', 'Gotita', 'Guadalupe', 'America', 'Britney Guadalupe']
li.sort()
#>>> li = ['Gaby', 'Gotita', 'Guadalupe', 'America', 'Britney Guadalupe']
#li.sort()
#>>> >>> li
#['America', 'Britney Guadalupe', 'Gaby', 'Gotita', 'Guadalupe']

#--Matrices
M = [[1, 2, 3],
[4, 5, 6],
[7, 8, 9]]
M
M[1]
#>>> M = [[1, 2, 3],
#[4, 5, 6],
#[7, 8, 9]]
#... ... >>> M
#[[1, 2, 3], [4, 5, 6], [7, 8, 9]]
#>>> M[1]
#[4, 5, 6]

#--Generar lista a partir de un rango de enteros
list(range(4))
#>>> list(range(4))
#[0, 1, 2, 3]
list(range(−6,7,2))

#--Suma de los elementos de la Matriz M, 
#--se mapean los vectores y se realiza la suma de todos sus elementos
list(map(sum, M))
#>>> list(map(sum, M))
#[6, 15, 24]

#-Diccionarios

D = {'nombre': 'Petra', 'edad': 30, 'tez': 'morena'}    #Declaración de un diccionario
D['nombre']
#>>> D = {'nombre': 'Petra', 'edad': 30, 'tez': 'morena'}
#>>> D['nombre']
#'Petra'

D = {}  #Un diccionario vacío
D['nombre'] = 'Gabius'  #Las claves se asignan de manera dinámica
D['edad'] = 16
D['trabajo'] = 'nurse'
#>>> D['nombre'] = 'Gabius'
#>>> D['edad'] = 16
#>>> D['trabajo'] = 'nurse'
#>>> D
#{'edad': 16, 'nombre': 'Gabius', 'trabajo': 'nurse'}

#--Diccionario cuyos elementos consisten de otros diccionarios
Dict = {'nombre': {'primer': 'Wilfrido', 'apellido': 'López'},
'trabajos': ['albañil', 'chalan'],'edad': 25}
#>>> Dict['nombre']
#{'primer': 'Wilfrido', 'apellido': 'López'}
#>>> Dict['trabajos']
#['albañil', 'chalan']

#-Tuplas

T = (1, 2, 3, 4)    #Declaración de una Tupla
#>>> T
#(1, 2, 3, 4)
len(T)  #Longitud
#>>> len(T)
#4
T[0]    #Elemento por posición
#>>> T[0]
#1
T.index(2)  #Posición del elemento en la tupla
#>>> T.index(2)
#1
T.count(2)  #Apariciones del elemento en la tupla
#>>> T.count(2)
#1
T = 'Hola', 3.0, [11, 22, 33]   #Declaración de Tupla con diferentes tipos de elementos
#>>> T
#('Hola', 3.0, [11, 22, 33])


#-Archivos

#---Abrir un archivo de texto en modo escritura
f = open('C:\\Users\Yovany\\Documents\\GitHub\\MTCA.PrimerSemestre'+
    '\\LenguajesProgramacion\\PrimerParcial\\data.txt', 'w')
#---Escribir texto en el archivo
f.write('Hola\n')   #Retorna el número de caracteres escritos
f.write('Mundo\n')
#---Cerrar el archivo
f.close()

#---Abrir el archivo de texto para lectura
f = open('C:\\Users\Yovany\\Documents\\GitHub\\MTCA.PrimerSemestre'+
    '\\LenguajesProgramacion\\PrimerParcial\\data.txt')
#---Leer el contenido del archivo
text = f.read()
#---Imprimir el contenido
print(text)

#>>> f = open('C:\\Users\Yovany\\Documents\\GitHub\\MTCA.PrimerSemestre'+
#    '\\LenguajesProgramacion\\PrimerParcial\\data.txt', 'w')
#... >>> f.write('Hola\n')
#5
#>>> f.write('Mundo\n')
#6
#>>> f.close()
#>>> f = open('C:\\Users\Yovany\\Documents\\GitHub\\MTCA.PrimerSemestre'+
#    '\\LenguajesProgramacion\\PrimerParcial\\data.txt')
#
#... >>> >>> text = f.read()
#>>> text
#'Hola\nMundo\n'
#>>> print(text)
#Hola
#Mundo

#-Conjuntos, Set
#--Son colecciones desordenadas de objetos únicos e inmutables.

#---Formas de declarar un conjunto
X = set('hola')
Y = {'m','u','n','d','o'}

#--Operaciones de Conjuntos
#---Intersección
X&Y
#>>> X&Y
#{'o'}
#---Unión
X|Y
#>>> X|Y
#{'u', 'h', 'o', 'n', 'm', 'l', 'a', 'd'}