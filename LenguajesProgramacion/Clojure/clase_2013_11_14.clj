;¡¡¡¡IMPORTANTE!!!!: Siempre declarar el tratamiento para listas vacías...
;De lo contrario se puede ciclar esta cosa del demonio y sin avisar... XD

;(map str [1 2 3])
;Función Anónima
;1
;(fn [x] (+ 1 x))
;2
;#(+ 1 %1)
;Filter
;(filter #(> %1 10)
;    [5 11 9 10])
;Reduce
;(reduce * [1 2 3 4])

;Función Mayor
(defn greater
    ([] nil)
    ([n] n)
    ([n1 n2]
        (if (nil? n1)
            (if (nil? n2)
                nil
                n2)
            (if (nil? n2)
                n1
                (if (> n1 n2)
                    n1
                    n2)))))

(reduce greater [5 8 10])

;Invertir una lista
(defn invertList
    ([li]
        (if (empty? li)
            '()
            (invertList (rest li) (list (first li)))))
    ([li lifinal]
        (if (empty? li)
            lifinal
            (recur (rest li) (cons (first li) lifinal)))))
;Mi definición de Map
(defn myMap
    ([f li]
        (if (empty? li )
            '()
            (myMap f 
                (rest li) 
                (list (f (first li))))))
    ([f li f_li]
        (if (empty? li)
            (invertList f_li)
            (recur f (rest li) (cons (f (first li)) f_li)))))

;Mi definición de Filter
(defn myFilter
    ([f li]
        (if (empty? li)
            '()
            (myFilter f
                (rest li)
                (if (f (first li)) 
                    (list (first li))
                    '()))))
    ([f li final_li]
        (if (empty? li)
            (invertList final_li)
            (recur f (rest li) (if (f (first li))
                                (cons (first li) final_li)
                                final_li)))))

;Se retorna una lista libre de apariciones del elemento
(defn itemDistinct
    [item li]
    (if (empty? li)
        '()
        (myFilter (fn [x] (not (= item x))) li)))

;Función que determina el número de apariciones del elemento en una lista
(defn itemCount
    [item li]
    (if (empty? li)
        0
        (count (myFilter (fn [x] (= item x)) li))))

;Concatena la primera lista con la segunda. Aún no se para que la generé... jeje
(defn listConcat    
    ([li1 li2]
        (if (empty? li1)
            (if (empty? li2)
                '()
                li2)
            (if (empty? li2)
                li1
                (listConcat (invertList li1) li2 li2))))
    ([li1 li2 liOut]
        (if (empty? li1)
            liOut
            (recur (rest li1) li2 (conj liOut (first li1))))))

(defn pow [x y] 
    (Math/pow x y))

(defn sqrt [x] 
    (Math/sqrt x))

;Funciones mas avanzadas...

;Promedio
(defn average 
    ([li]
        (if (empty? li)
            0
            (average (rest li) (first li) (count li))))
    ([li sum items]
        (if (empty? li)
            (/ sum items)
            (recur (rest li) (+ sum (first li)) items))))

; '({:nombre "yovany" :edad 28} {:nombre "mauricio" :edad 26})
;({:nombre "yovany" :edad 30} :edad)
;(map (fn [x] (x :edad)) '({:nombre "yovany" :edad 28} {:nombre "mauricio" :edad 26}))
;(28 26)

;Frecuencia
(defn freq
    ([li]
        (if (empty? li)
            '()
            (freq li '())))
    ([li liOut]
        (if (empty? li)
            liOut
            ;Se procesa el primer elemento de la lista
            ;es la única vez que se hará para dicho elemento
            ;Se retorna una lista libre de apariciones de dicho elemento
            (recur (itemDistinct (first li) li);(freq li (first li) '()) 
                (cons [(first li) 
                        ;Función que determina el número de apariciones del i-ésimo elemento en la lista actual
                        (itemCount (first li) li)] 
                    liOut)))))

;desviacion estandard
(defn stDeviation
    ([li] 
        (stDeviation li (average li) '()))
    ([li avg liOut]
        (if (empty? li)
            ;s = sqrt(sumatoria((xi - xavg)^2)/(N-1))
            (sqrt (average (rest liOut) (first liOut) (- (count liOut) 1)))
            (recur (rest li) avg (cons (pow (- (first li) avg) 2) liOut)))))

(defn ageDeviation
    [li]
    ;Obtener edades y filtrar los elementos que sean nulos, 
    ;ocurre un error en el cálculo de la desviación estándar si hay un elemento nulo en la lista
    (stDeviation (myFilter (fn [x] (not (= x nil))) (myMap (fn [x] (x :edad)) li))))
;'({:nombre "yovany" :edad 28} {:nombre "mauricio" :edad 26})

;mediana

;moda