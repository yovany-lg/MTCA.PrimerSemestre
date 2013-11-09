#-Funciones
contar(lista) #El llamado de funciones se realiza de esta manera.
InstanciaClase.contar(lista) #De esta manera se realiza el llamado a métodos
print('a','b','c', sep='*') #Ejemplo de llamado a imprimir
#>>> print('a','b','c', sep='*')
#a*b*c

#-Definición de funciones
#-Función que realiza la intersección de dos cadenas
def intersect(seq1, seq2):
    res = []  # lista inicial vacía
    for x in seq1:  # Recorrer seq1
        if x in seq2:  # verificar el el elemento es común
            res.append(x)  # Se agrega al final de la lista
    return res

#>>> s1 = "hola"
#>>> s2 = "mundo"
#>>> intersect(s1,s2)
#['o']
#---Retorno de múltiples valores en forma de tuplas
def intersect(seq1, seq2):
    res = []  # lista inicial vacía
    for x in seq1:  # Recorrer seq1
        if x in seq2:  # verificar el el elemento es común
            res.append(x)  # Se agrega al final de la lista
    return res, len(res)    # Se retorna la longitud de la lista de intersección
#>>> intersect(s1,s2)
#(['o'], 1)

#---Para funciones que recirben múltiples parámetros de entrada
def myfunc(*args): print(args)   #Se toman los parámetros *args en forma de una tupla
#>>> myfunc(1,2,3)
#(1, 2, 3)

def f(**args): print(args)  #Se toman los parámetros **args en forma de diccionario
#>>> f(a=1, b=2)
#{'b': 2, 'a': 1}

