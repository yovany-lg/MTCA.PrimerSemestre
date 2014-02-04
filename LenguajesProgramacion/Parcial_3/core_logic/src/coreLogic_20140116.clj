;https://github.com/clojure/core.logic/wiki/A-Core.logic-Primer
(use 'clojure.core.logic)

(run* [q]
   (membero q [1 2 3])
   (membero q [2 3 4]))


;The Reasoned Schemer

(run* [q] u#)

(run* [q] #u (== #t q))

(run* [q] (== true q))

(run* [q] 
    u# ;Objetivo, meta, Proposición lógica que falla
    (== true q))

(run* [q] 
    s# ;Objetivo, meta, Proposición lógica que es exitoso
    (== true q))    ;Se asocia el valor lógico TRUE a q

(run* [r] 
    s# ;Objetivo, meta, Proposición lógica que es exitoso
    (== "corn" r))    ;Se asocia el valor "corn" a r

(run* [r] 
    u# ;Objetivo, meta, Proposición lógica que falla
    (== "corn" r))

(run* [x]
    (let [x true]
        (== true x)))
;(_0)   ;Retorna un símbolo de una variable fresca, ya que los goals dentro de let son exitosos
(run* [x]
    (let [x true]
        (== false x)))

(run* [q]   ;También q inicia como variable fresca
    (fresh [x] ;Una variable es "fresh" (Carne fresca!!!... XD) cuando no tiene asociación alguna
        (== true  x)
        (== true q)))

(run* [x]
    s#)

(run* [x]
    (let [x false]
        (fresh [x]
            (== true x))))  ;La x es la de fresh, no la original de run* ni la de let
;(_0)

(run* [q]
    (== false q)    ;Este goal es exitoso
    (== true q))    ;Esta asociación no se puede realizar, ya que q no es mas una variable fresca
;()

(run* [q]
    (== false q)    ;Este goal es exitoso
    (== false q))    ;Esta asociación es exitosa aunque q ya no es una variable fresca, ya que false esta asociado a q
;(false)

(run* [q]
    (fresh [x]
        (== x q)    ;Esta asociación se asegura que cualquier asociación a x también es asociada a q
        (== true x)))
;(true)

(run* [x]
    (conde  ;Condiciones, cond"e" every line
        ((== "olive" x) s#) ;La asociación es exitosa y s# preserva la asociación
        ((== "oil" x) s#)   ;Se asume que la línea anterior falló, por lo que se refresca a x, nuevamente se preserva la asociación
        (s# u#)))   ;Algo análogo al else, en este caso se asume como una 
;("olive" "oil")

(run 1 [x]  ;run 1 produce a lo más un valor
    (conde  ;Condiciones, cond"e" every line
        ((== "olive" x) s#) ;La asociación es exitosa y s# preserva la asociación
        ((== "oil" x) s#)   ;Se asume que la línea anterior falló, por lo que se refresca a x, nuevamente se preserva la asociación
        (s# u#)))   ;Algo análogo al else, en este caso se asume como una 


;En conde cada línea es una pregunta (Pregunta ...), si la pregunta es verdadera o exitosa, se sigue con la respuesta, si la respuesta es exitosa se considerará como salida

(run* [x]
    (conde
        ((== "virgin" x) u#)
        ((== "olive" x) s#)
        (s# s#) ;Devuelve _0, ya que la líena fue exitosa pero a x no se le asoció valor alguno
        ((== "oil" x) s#)
        (s# u#)))
;("olive" _0 "oil")

(run 2 [x] ;Solo devuelve los primeros dos valores
    (conde
        ((== "extra" x) s#)
        ((== "virgin" x) u#)
        ((== "olive" x) s#)
        ((== "oil" x) s#)
        (s# u#)))
;("extra" "olive")

(run* [r]
    (fresh [x y]
        (conde
            ((== "split" x) (== "pea" y))
            ((== "navy" x) (== "bean" y))
            (s# u#))
        (== (cons x (cons y '())) r)))