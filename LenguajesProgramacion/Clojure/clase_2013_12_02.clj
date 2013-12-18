;Desarrollar la sig. API para el manejo de matrices

;*(m-nueva filas columnas)   => matriz
;*(m-filas matriz)   => num
;*(m-columnas matriz)    => num
;*(m-fila matriz fila)   => vector
;*(m-columna matriz columna) => vector
;*(m-producto-escalar matriz escalar)    => matriz
;*(m-multi matriz1 matriz2)  => matriz
;(m-inversa matriz1)    => matriz *Transpuesta
;*(m-transpuesta matriz) => matriz
;*(m-cambiar-fila matriz nfila cual) ;nfila = vector, cual = cual fila
;*(m-cambiar-clumna matriz ncol cual) ;ncol = vector, cual = cual col
;*(m-suma matriz1 matriz2)
;----------------------------
;;Otras fn necesarias
;Asociación???
;(m-assoc matriz fila col valor) ;Cambia el valor de una celda

;{:f1 {:c1 :c2}}

;Ejemplo del formato utilizado para una matriz
(def m1 {:row2 {:col1 -3}, :row3 {:col1 -2}, :row1 {:col1 1}})
(def m2 {:row2 {:col1 3}, :row3 {:col1 2}, :row1 {:col1 -1}})
(def B {:row3 {:col3 9, :col2 8, :col1 7}, :row2 {:col3 6, :col2 5, :col1 4}, :row1 {:col3 3, :col2 2, :col1 1}})


(defn matrixNewRow
    "Generar una nueva fila"
    [id cols]
    (hash-map (keyword (str "row" id)) 
        (reduce #(merge %1 (hash-map (keyword %2) 0)) {} (map #(str "col" %1) (range 1 (+ cols 1))))))

(defn matrixNew
    "Genera una matriz con filas y columnas"
    [rows cols]
    (reduce #(merge %1 (matrixNewRow %2 cols))
        {} 
        (range 1 (+ rows 1))))

(defn matrixRowsNum
    "Devuelve la cantidad de filas en una matriz"
    [matrix]
    (count matrix))

(defn matrixColsNum
    "Devuelve la cantidad de columnas en una matríz"
    [matrix]
    (count (matrix :row1)))

(defn matrixRow
    "Devuelve una fila en un vector"
    [matrix row]
    (vec (map #(get %1 1) (sort-by key (get matrix (keyword (str "row" row)))))))

(defn matrixRowsVectors
    "Devuelve una matriz en un vector de filas"
    [matrix]
    (vec (map (fn [x] (vec (map #(get % 1) (sort-by key (get x 1))))) (sort-by key matrix))))

(defn matrixCol
    "Devuelve una columna en un vector"
    [matrix col]
    (vec (map #(get (get %1 1) (keyword (str "col" col))) (sort-by key matrix))))

(defn matrixColsVectors
    "Devuelve una matriz en un vector de columnas"
    ([matrix]
        (matrixColsVectors (sort-by key matrix) [] 1 (matrixColsNum matrix)))
    ([sortedMatrix colsVect n colsNum]
        (if (<= n colsNum)
            (recur sortedMatrix (conj colsVect (vec (map #(get (get %1 1) (keyword (str "col" n))) sortedMatrix))) (inc n) colsNum)
            colsVect)))

(defn rowScalarProduct
    "Realiza la multiplicación de una fila por un escalar"
    [row x]
    (reduce (fn [newRow col] (merge newRow (hash-map (key col) (* (val col) x)))) {} row))

(defn matrixScalarProduct
    "Realiza la multiplicación de una matríz por un escalar"
    [matrix x]
    (reduce (fn [newMatrix row] (merge newMatrix (hash-map (key row) (rowScalarProduct (val row) x)))) {} matrix))

(defn rowColMult
    "Multiplicación de una fila por una columna, como parte de una multiplicación entre matrices"
    [row col]   ;En forma de vector (los elementos estan ordenados)
    (reduce + (map * row col)))

(defn multByRowCols
    "Multiplicación de una fila por varias columnas, como parte de una multiplicación entre matrices"
    ([rowVector colsVector]
        (multByRowCols rowVector colsVector []))
    ([rowVector colsVector rowOut]
        (if (empty? colsVector)
            rowOut
            (recur rowVector 
                (rest colsVector) 
                (conj rowOut 
                    (rowColMult rowVector (first colsVector)))))))

(defn rowVectToMap
    "Convierte un vector fila a un hash-map"
    ([rowVect]
        (rowVectToMap rowVect 1 {}))
    ([rowVect rowCount hashOut]
        (if (empty? rowVect)
            hashOut
            (recur (rest rowVect) 
                (inc rowCount) 
                (merge hashOut 
                    (hash-map (keyword (str "col" rowCount)) (first rowVect)))))))

(defn matrixMult
    "Multiplicación de matrices"
    ([A B]
        (if (= (matrixColsNum A) (matrixRowsNum B))
            (matrixMult (matrixRowsVectors A) (matrixColsVectors B) {} 1)
            nil))
    ([rowsVectors colsVectors ABMatrix rowCount]
        (if (empty? rowsVectors)
            ABMatrix
            (recur (rest rowsVectors) 
                colsVectors 
                (merge ABMatrix 
                    (hash-map (keyword (str "row" rowCount)) 
                        (rowVectToMap (multByRowCols (first rowsVectors) colsVectors))))
                (inc rowCount)))))

(defn rowsVectorsToMatrix
    "Genera una matriz a partir de un vector de filas (en forma de vector)"
    ([rowsVectors]
        (rowsVectorsToMatrix rowsVectors {} 1))
    ([rowsVectors matrixOut rowCount]
        (if (empty? rowsVectors)
            matrixOut
            (recur (rest rowsVectors) 
                (merge matrixOut (hash-map (keyword (str "row" rowCount)) (rowVectToMap (first rowsVectors)))) 
                (inc rowCount)))))

(defn matrixToStr
    "Genera una cadena imprimible de una matríz"
    ([matrix]
        (matrixToStr (matrixRowsVectors matrix) ""))
    ([rowsVectors strOut]
        (if (empty? rowsVectors)
            strOut
            (recur (rest rowsVectors) (str strOut (reduce (fn [string x] (str string x "\t")) "" (first rowsVectors)) "\n")))))

(defn matrixTranspose
    "Transpuesta de una matríz"
    [matrix]
    (rowsVectorsToMatrix (matrixColsVectors matrix)))

(defn rowReplace
    "Reemplazar una fila por otra (en forma de vector)"
    [matrix rowVector rowIndex]
    (merge-with merge matrix (hash-map (keyword (str "row" rowIndex)) (rowVectToMap rowVector))))

(defn colReplace
    "Reemplazar una columna por otra (en forma de vector)"
    ([matrix colVector colIndex]
        (colReplace (sort-by key matrix) colVector {} colIndex))
    ([sortedMatrix colVector matrixOut colIndex]
        (if (empty? sortedMatrix)
            matrixOut
            (recur (rest sortedMatrix) 
                (rest colVector) 
                (let [currentRow (first sortedMatrix)]
                    (merge matrixOut 
                        (hash-map (key currentRow) 
                            (merge (val currentRow) 
                                (hash-map (keyword (str "col" colIndex)) (first colVector))))))
                colIndex))))

(defn matrixAdition
    "Suma de matrices"
    [A B]
    (merge-with (fn [m1 m2] (merge-with + m1 m2)) A B))

(defn matrixCellAssoc
    "Reemplazar una celda"
    [A row col value]
    (merge-with merge A {(keyword (str "row" row)) {(keyword (str "col" col)) value}}))
