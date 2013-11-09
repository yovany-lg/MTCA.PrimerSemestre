#-Estructuras if/elif/else

x = 1
y = 2

if x > y:
    x
else:
    y

#>>> if x > y:
#    x
#else:
#    y
#2

#-Estructuras while
while True:
    reply = input('Introduce un texto:')
    if reply == 'stop': break#Se detiene al introducir 'stop'
    print(reply.upper())

#>>> while True:
#    reply = input('Introduce un texto:')
#    if reply == 'stop': break#Se detiene al introducir 'stop'
#    print(reply.upper())

#Introduce un texto:hola
#HOLA
#Introduce un texto:yovany
#YOVANY
#Introduce un texto:Gaby
#GABY
#Introduce un texto:stop    

while True:
    reply = input('Introduce Texto:')
    if reply == 'stop': #se detiene al introducir 'stop'
        break
    elif not reply.isdigit():   #Muestra un mensaje si no se introdujo un número
        print('oh snap!!' * 8)
    else:
        print(int(reply) ** 2)  #Eleva el número introducido al cuadrado
print('Bye')    #mensaje de despedida...
#>>> while True:
#    reply = input('Introduce Texto:')
#    if reply == 'stop':
#        break
#    elif not reply.isdigit():
#        print('oh snap!!' * 8)
#    else:
#        print(int(reply) ** 2)
#print('Bye')

#Introduce Texto:hola
#oh snap!!oh snap!!oh snap!!oh snap!!oh snap!!oh snap!!oh snap!!oh snap!!
#Introduce Texto:1
#1
#Introduce Texto:2
#4
#Introduce Texto:3
#9
#Introduce Texto:stop
#Bye
y = 6
x = y // 2  # Para un número mayor que 1
while x > 1:
    if y % x == 0:  # evaluar el residuo
        print(y, 'tiene un factor', x)
        break  # Omitir la opción else
    x -= 1
else:  # Cuando se terminó el bucle de manera normal
    print(y, 'es un número primo')
#2 es un número primo
#3 es un número primo
#4 tiene un factor 2
#6 tiene un factor 3

#--Estructuras for

#---Recorrido de listas
for x in ["hola", "yova", "gaby"]:
    print(x, end=' ')
#>>> for x in ["hola", "yova", "gaby"]:
#    print(x, end=' ')

#hola yova gaby 
#---Recorrido de cadenas
S = "gotita"
for x in S: print(x, end=' ')
#>>> S = "gotita"
#for x in S: print(x, end=' ')
#
#g o t i t a 

#---Recorrido de diccionarios
D = {'a': 1, 'b': 2, 'c': 3}
for key in D:
    print(key, '=>', D[key])
>>> D = {'a': 1, 'b': 2, 'c': 3}
#for key in D:
#    print(key, '=>', D[key])
#
#c => 3
#b => 2
#a => 1

#---Ciclos for en rangos
for i in range(3):
    print(i, 'gotitas')

#>>> for i in range(3):
#    print(i, 'gotitas')
#
#0 gotitas
#1 gotitas
#2 gotitas

