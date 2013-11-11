(defn caracteres 
    ;Funcion para obtener una cadena a partir de un caracter repetiro n veces
    ([car cuantos]
        (caracteres car cuantos (str "")))
    ([car cuantos cad]
        (if (> cuantos 0)
            (recur car (dec cuantos) (str cad car))
            cad)))
;Generar nivel con listas
(defn nivel
    ([li car]
        (nivel li car "" 0))
    ([li car cad nitem]
        (if ())))
