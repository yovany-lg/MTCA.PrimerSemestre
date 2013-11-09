'''
Created on Oct 17, 2013

@author: yovany
'''
#Una clase Persona
class Person:
    def __init__(self, name, job=None, pay=0):  #Inicioalización de los atributos
        self.name = name  
        self.job = job  
        self.pay = pay        

    def lastName(self):  # 
        return self.name.split()[-1]  # self denota la instancia en cuestión
    
    def giveRaise(self, percent):
        self.pay = int(self.pay * (1 + percent))  # Aumenta el salario de una persona

    def __repr__(self):  # Muestra una estructura general de la instancia
        return '[Person: %s, %s]' % (self.name, self.pay)  # String to print        

    def __setattr__( self, attrname, value):    #para agregar nuevos atributos a la instancia
        self.__dict__[attrname] = value

    def __getattr__(self, attrname):    #Para procesar solicitudes de atrributos que no existen
        if attrname in self.__dict__:
            return self.__dict__[attrname]
        else:
            return 'El atributo \'%s\' no existe...' % (attrname)

#Una clase Manager que se deriva de la clase Persona
class Manager(Person):
    def __init__(self, name, pay):  # constructor redefinido para la clase Manager
        Person.__init__(self, name, 'mgr', pay)  # Se ejecuta el constructor original con el trabajo 'mgr'
        
    def giveRaise(self, percent, bonus=.10):
        Person.giveRaise(self, percent + bonus)  # Aumento de salario en el método original

    def __getattr__(self, attr):
            return getattr(self.person, attr)  # Permite que los atributos nuevos los gestione la clase original
        
#    def __repr__(self):
#        return str(self.person)  # Must overload again (in 3.X

class Department:
    def __init__(self, *args):  #Constructor con elementos que se mapean en una lista
        self.members = list(args)
        
    def addMember(self, person):    #Agregar un nuevo miembro
        self.members.append(person)

    def giveRaises(self, percent):  #Asigna aumentos de salario a cada uno de los miembros del departamento
        for person in self.members:
            person.giveRaise(percent)
            
    def showAll(self):  #Muestra todos los miembros del departamento
        for person in self.members:
            print(person)


#--Casos de prueba
bob = Person('Bob Smith')
sue = Person('Sue Jones', job='dev', pay=100000)
tom = Manager('Tom Jones', 50000)
development = Department(bob, sue)
development.addMember(tom)
development.giveRaises(.10)
development.showAll()

#[Person: Bob Smith, 0]
#[Person: Sue Jones, 110000]
#[Person: Tom Jones, 60000]

#--Adición de nuevos atributos
bob.edad = 30
#>>> bob.edad
#30
