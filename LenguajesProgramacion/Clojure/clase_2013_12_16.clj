;--------MANEJO DE MATRICES
(defn matrixNewRow
    [id cols]
    (hash-map (keyword (str "row" id)) 
        (reduce #(merge %1 (hash-map (keyword %2) 0)) {} (map #(str "col" %1) (range 1 (+ cols 1))))))

(defn matrixNew
    [rows cols]
    (reduce #(merge %1 (matrixNewRow %2 cols))
        {} 
        (range 1 (+ rows 1))))

(defn matrixRowsNum
    [matrix]
    (count matrix))

(defn matrixColsNum
    [matrix]
    (count (matrix :row1)))

(defn matrixRow
    [matrix row]
    (vec (map #(get %1 1) (sort-by key (get matrix (keyword (str "row" row)))))))

(defn matrixRowsVectors
    [matrix]
    (vec (map (fn [x] (vec (map #(get % 1) (sort-by key (get x 1))))) (sort-by key matrix))))

(defn matrixCol
    [matrix col]
    (vec (map #(get (get %1 1) (keyword (str "col" col))) (sort-by key matrix))))

(defn matrixColsVectors
    ([matrix]
        (matrixColsVectors (sort-by key matrix) [] 1 (matrixColsNum matrix)))
    ([sortedMatrix colsVect n colsNum]
        (if (<= n colsNum)
            (recur sortedMatrix (conj colsVect (vec (map #(get (get %1 1) (keyword (str "col" n))) sortedMatrix))) (inc n) colsNum)
            colsVect)))

(defn rowScalarProduct
    [row x]
    (reduce (fn [newRow col] (merge newRow (hash-map (key col) (* (val col) x)))) {} row))

(defn matrixScalarProduct
    [matrix x]
    (reduce (fn [newMatrix row] (merge newMatrix (hash-map (key row) (rowScalarProduct (val row) x)))) {} matrix))

(defn rowColMult
    [row col]   ;En forma de vector (los elementos estan ordenados)
    (reduce + (map * row col)))

(defn multByRowCols
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
    ([rowsVectors]
        (rowsVectorsToMatrix rowsVectors {} 1))
    ([rowsVectors matrixOut rowCount]
        (if (empty? rowsVectors)
            matrixOut
            (recur (rest rowsVectors) 
                (merge matrixOut (hash-map (keyword (str "row" rowCount)) (rowVectToMap (first rowsVectors)))) 
                (inc rowCount)))))

(defn matrixToStr
    ([matrix]
        (matrixToStr (matrixRowsVectors matrix) ""))
    ([rowsVectors strOut]
        (if (empty? rowsVectors)
            strOut
            (recur (rest rowsVectors) (str strOut (reduce (fn [string x] (str string x "\t")) "" (first rowsVectors)) "\n")))))

(defn matrixTranspose
    [matrix]
    (rowsVectorsToMatrix (matrixColsVectors matrix)))

(defn rowReplace
    [matrix rowVector rowIndex]
    (merge-with merge matrix (hash-map (keyword (str "row" rowIndex)) (rowVectToMap rowVector))))

(defn colReplace
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
    [A B]
    (merge-with (fn [m1 m2] (merge-with + m1 m2)) A B))

(defn matrixCellAssoc
    [A row col value]
    (merge-with merge A {(keyword (str "row" row)) {(keyword (str "col" col)) value}}))

(defn matrixCell 
    [A row col]
    (get-in A [(keyword (str "row" row)) (keyword (str "col" col))]))
;---------------------




;-------------------Problena de las N reinas en un tablero de ajedrez
(defn round [x] 
    (Math/round x))

(defn abs [x] 
    (Math/abs x))

(defn solAdjust
    [posVect N]
    (vec (map (fn [q] 
            (let [qad (+ q (- 1 (rand-int 3)))]
                (if (> qad N)
                    N
                    (if (< qad 1)
                        1
                        qad)))) posVect)))

(defn takeMemSol
    [hMem hms]
    ((get hMem (rand-int hms)) :posVect))

(defn randSol
    [N]
    (vec (map #(+ % (rand-int N)) (repeat N 1))))

(defn newSol
    [N hMS mCR pAR hMem]
    (if (<= (rand 1) mCR)
        (let [sol (takeMemSol hMem hMS)]
            (if (<= (rand 1) pAR)
                (solAdjust sol N)
                sol))
        (randSol N)))

(defn verFnVal ;Función de búsqueda vertical
    ([posVect]
        (/ (reduce + (filter #(> % 1) (vals (frequencies posVect)))) (count posVect))))

(defn diagMatch
    [v1 v2]
    (reduce = (map #(abs (- %1 %2)) v1 v2)))

(defn diagEval
    ([row col posVect]
        (diagEval row col (inc row) posVect 0))
    ([row col currentRow posVect matches]
        (if (empty? posVect)
            matches
            (recur row 
                col 
                (inc currentRow) 
                (rest posVect)
                (if (diagMatch [row col] [currentRow (first posVect)])
                    (inc matches)
                    matches)))))

(defn diagFnVal    ;Función de búsqueda en diagonal 
    ([posVect]
        (diagFnVal posVect 1 (count posVect) 0))
    ([posVect currentRow size res]
        (if (empty? posVect)
            (/ res size)
            (recur (rest posVect) 
                (inc currentRow) 
                size
                (+ res (diagEval currentRow (first posVect) (rest posVect)))))))

(defn queensFnVal
    [posVect]
        (+ (verFnVal posVect) (diagFnVal posVect)))

(defn solDuplicated
    [hMem sol]
    (reduce #(or %1 (= sol (%2 :posVect))) false hMem))

(defn queensHMInit
    ([N hms]
        (queensHMInit N hms []))
    ([N hms hmOut]
        (if (> hms 0)
            (let [posVect (randSol N)]
                (if (solDuplicated hmOut posVect)
                    (recur N hms hmOut)
                    (recur N 
                        (dec hms) 
                        (conj hmOut {:posVect posVect
                                    :fnVal (queensFnVal posVect)}))))
            (vec (sort-by :fnVal hmOut)))))

(defn generateNewSol
    [N hMS mCR pAR hMem]
    (let [sol (newSol N hMS mCR pAR hMem)]
        (let [fnVal (queensFnVal sol)]
            (if (or (< ((last hMem) :fnVal) fnVal) (solDuplicated hMem sol))
                (recur N hMS mCR pAR hMem)
                {:posVect sol :fnVal fnVal}))))

(defn queensSolveByHS
    ([N hMS mCR pAR iter]
        (queensSolveByHS N hMS mCR pAR (queensHMInit N hMS) iter))
    ([N hMS mCR pAR hMem iter]
        (if (> iter 0)
            (recur N hMS mCR pAR (vec (take hMS (sort-by :fnVal (conj hMem (generateNewSol N hMS mCR pAR hMem))))) (dec iter))
            (vec (take hMS (sort-by :fnVal hMem))))))

(defn queensMatrix
    ([posVect]
        (queensMatrix posVect (matrixNew (count posVect) (count posVect)) 1))
    ([posVect matrixOut rowIndex]
        (if (empty? posVect)
            matrixOut
            (recur (rest posVect) (matrixCellAssoc matrixOut rowIndex (first posVect) "Q") (inc rowIndex)))))







(queensSolveByHS 8 50 0.7 0.2 1000)
[{:posVect [2 7 5 8 1 4 6 3], :fnVal 0} {:posVect [4 8 1 3 6 2 7 5], :fnVal 0} {:posVect [2 7 3 6 8 5 1 4], :fnVal 0} {:posVect [7 4 2 8 6 1 3 5], :fnVal 0} {:posVect [5 7 1 3 8 6 4 2], :fnVal 0} {:posVect [6 3 1 8 5 2 4 7], :fnVal 0} {:posVect [4 7 5 3 1 6 8 2], :fnVal 0} {:posVect [6 8 7 1 3 5 2 4], :fnVal 1/8} {:posVect [2 8 5 7 4 1 3 6], :fnVal 1/8} {:posVect [3 8 2 5 1 7 4 6], :fnVal 1/8} {:posVect [3 7 4 8 1 5 6 2], :fnVal 1/8} {:posVect [6 4 2 8 5 7 3 1], :fnVal 1/8} {:posVect [8 2 7 1 3 5 6 4], :fnVal 1/8} {:posVect [3 6 4 8 1 5 7 2], :fnVal 1/8} {:posVect [2 5 8 4 7 3 6 1], :fnVal 1/8} {:posVect [8 1 4 6 3 2 7 5], :fnVal 1/8} {:posVect [4 8 1 5 2 6 3 7], :fnVal 1/8} {:posVect [5 8 1 3 6 2 7 4], :fnVal 1/8} {:posVect [3 8 2 4 1 7 5 6], :fnVal 1/8} {:posVect [7 4 2 8 5 1 3 6], :fnVal 1/8} {:posVect [2 5 8 6 3 1 7 4], :fnVal 1/8} {:posVect [8 6 2 4 1 7 5 3], :fnVal 1/8} {:posVect [2 8 6 3 1 4 7 5], :fnVal 1/8} {:posVect [7 3 2 8 5 1 4 6], :fnVal 1/8} {:posVect [3 7 2 4 5 1 8 6], :fnVal 1/8} {:posVect [3 8 2 5 1 6 4 7], :fnVal 1/8} {:posVect [3 8 6 2 5 7 1 4], :fnVal 1/8} {:posVect [3 8 6 1 2 5 7 4], :fnVal 1/8} {:posVect [2 4 8 7 5 3 1 6], :fnVal 1/8} {:posVect [8 4 2 7 6 1 3 5], :fnVal 1/8} {:posVect [3 7 2 4 6 1 8 5], :fnVal 1/8} {:posVect [2 4 8 6 3 1 7 5], :fnVal 1/8} {:posVect [5 8 1 4 7 2 6 3], :fnVal 1/8} {:posVect [4 7 1 3 6 2 8 5], :fnVal 1/8} {:posVect [3 8 7 2 4 6 1 5], :fnVal 1/8} {:posVect [1 3 8 6 4 2 7 5], :fnVal 1/8} {:posVect [4 7 1 3 5 2 8 6], :fnVal 1/8} {:posVect [6 3 1 7 4 2 5 8], :fnVal 1/8} {:posVect [4 6 1 3 7 2 8 5], :fnVal 1/8} {:posVect [8 2 5 7 3 1 6 4], :fnVal 1/8} {:posVect [8 4 2 7 5 1 3 6], :fnVal 1/8} {:posVect [4 7 5 2 1 6 8 3], :fnVal 1/8} {:posVect [2 3 8 6 4 1 7 5], :fnVal 1/8} {:posVect [8 3 5 7 2 1 6 4], :fnVal 1/8} {:posVect [4 8 5 2 1 6 7 3], :fnVal 1/4} {:posVect [7 5 2 8 6 8 3 1], :fnVal 1/4} {:posVect [4 8 1 3 7 2 7 5], :fnVal 1/4} {:posVect [4 7 1 3 6 2 7 5], :fnVal 1/4} {:posVect [6 8 2 4 7 5 3 1], :fnVal 1/4} {:posVect [3 7 4 8 1 5 7 2], :fnVal 1/4}]

user=> (print (matrixToStr (queensMatrix [2 7 5 8 1 4 6 3])))
0   Q   0   0   0   0   0   0   
0   0   0   0   0   0   Q   0   
0   0   0   0   Q   0   0   0   
0   0   0   0   0   0   0   Q   
Q   0   0   0   0   0   0   0   
0   0   0   Q   0   0   0   0   
0   0   0   0   0   Q   0   0   
0   0   Q   0   0   0   0   0   









(defn subvector
    ([vect size]
        (subvector vect size 0 []))
    ([vect size vectIndex vectOut]
        (if (< vectIndex size)
            (recur vect size (inc vectIndex) (conj vectOut (get vect vectIndex)))
            vectOut)))