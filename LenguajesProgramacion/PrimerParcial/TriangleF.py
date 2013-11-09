'''
Created on 08/10/2013

@author: Administrador
'''

'''
Created on Oct 7, 2013

@author: yovany
'''
def spaces(n):
    return ' '*n

def dots(n):
    return '*'*n #Regular Triangle

def figure_level(li):
    cad = ""
    index = 1
    for j in li:
        if index%2 == 0:    #para elementos pares se escriben cracteres
            cad+=dots(j)
        else:   #para elementos impares se escriben espacios
            cad+=spaces(j)
        index+=1
    return cad+'\n'

def figure(li):
    cad = ""
    for i in li:    #Para cada nivel
        cad+=figure_level(i)
    return cad

#--Triangulo Completo
def full_triangle(N):
    n=1
    levels = []
    while n<=N:
        levels.append([(N-n),(2*n-1)])  #[Espacios,Relleno] 
        n+=1
    return figure(levels)
    

#---Triangulo vacío
def empty_triangle(N):
    n=1
    levels = []
    while n<=N:
        levels.append(empty_triangle_level(n,N))   #listas de Niveles
        n+=1
    return figure(levels)
    #print(levels)

def empty_triangle_level(n,N):
    return triangle_spaces(n,N)+empty_triangle_dots(n,N)    #[Espacios,Relleno,Espacios...]
    
def triangle_spaces(n,N):
    return [N-n]

def empty_triangle_dots(n,N):
    li = []
    dots_count = 2*n-1
    
    if n == 1 or n == N:
        li.append(dots_count) #primer y ultimo nivel
    else:
        li+= [1,(dots_count-2),1] #el resto de los niveles
    return li 

#---Diamante vacío
def empty_diamond(N):
    levels=[]
    n=1
    while n<=N:
        levels.append(empty_diamond_level(n,N))   #listas de Niveles
        n+=1
    return figure(levels)
    #print(levels)

def empty_diamond_level(n,N):
    middle_level = int((N+1)/2)
    if n<=middle_level:
        return [middle_level-n]+empty_diamond_dots(n,middle_level)  #[espacios,relleno]
    else:
        return [n-middle_level]+empty_diamond_dots(N-n+1,middle_level)  #[espacios,relleno]

def empty_diamond_dots(n,N):
    li = []
    dots_count = 2*n-1
    if n == 1:
        li.append(dots_count) #primer
    else:
        li+= [1,(dots_count-2),1] #el resto de los niveles
    return li 

#---Diamante relleno
def full_diamond(N):
    levels=[]
    n=1
    while n<=N:
        levels.append(full_diamond_level(n,N))   #listas de Niveles
        n+=1
    return figure(levels)
    #print(levels)

def full_diamond_level(n,N):
    middle_level = int((N+1)/2)
    if n<=middle_level:
        return [middle_level-n]+full_diamond_dots(n,middle_level)  #[espacios,relleno]
    else:
        return [n-middle_level]+full_diamond_dots(N-n+1,middle_level)  #[espacios,relleno]

def full_diamond_dots(n,N):
    li = []
    dots_count = 2*n-1
    li.append(dots_count) #primer
    return li 



#---obsolete methods
def get_level(n,N):
    ndots = (2*n-1)
    nspaces = (N-n)
    return spaces(nspaces)+dots(ndots)+'\n'

def get_full_triangle(N):
    n=1
    cad = ""
    while n<=N:
        cad+=get_level(n,N)
        n+=1
    return cad


#---Casos de pruebas

#>>> print(full_triangle(3))
#  *
# ***
#*****
#
#>>> print(full_triangle(5))
#    *
#   ***
#  *****
# *******
#*********

#>>> print(empty_triangle(5))
#    *
#   * *
#  *   *
# *     *
#*********
#
#>>> print(empty_triangle(3))
#  *
# * *
#*****

#>>> print(empty_diamond(3))
# *
#* *
# *
#
#>>> print(empty_diamond(5))
#  *
# * *
#*   *
# * *
#  *

#>>> print(full_diamond(5))
#  *
# ***
#*****
# ***
#  *
#
#>>> print(full_diamond(3))
# *
#***
# *