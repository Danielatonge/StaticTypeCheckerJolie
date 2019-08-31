(declare-sort Type)
(declare-sort Term)

(declare-fun hasType (Term Type) Bool)
(declare-fun sameType (Term Term) Bool)
(declare-fun typeOf (Term) Type)

;; Define types
(declare-fun string () Type)
(declare-fun int () Type)
(declare-fun long () Type)
(declare-fun bool () Type)
(declare-fun double () Type)
(declare-fun void () Type)
(declare-fun raw () Type)
(declare-fun undefined () Type)
(declare-fun any () Type)

;; Ensure non-equality of types
(assert (not (= string int)))
(assert (not (= string long)))
(assert (not (= string bool)))
(assert (not (= string double)))
(assert (not (= string void)))
(assert (not (= string raw)))
(assert (not (= string undefined)))

(assert (not (= int long)))
(assert (not (= int bool)))
(assert (not (= int double)))
(assert (not (= int void)))
(assert (not (= int raw)))
(assert (not (= int undefined)))

(assert (not (= long bool)))
(assert (not (= long double)))
(assert (not (= long void)))
(assert (not (= long raw)))
(assert (not (= long undefined)))

(assert (not (= bool double)))
(assert (not (= bool void)))
(assert (not (= bool raw)))
(assert (not (= bool undefined)))

(assert (not (= double void)))
(assert (not (= double raw)))
(assert (not (= double undefined)))

(assert (not (= void raw)))
(assert (not (= void undefined)))

(assert (not (= raw undefined)))


;; Describe type functions behavior
(assert (forall ((t Term)) (= (hasType t string) (= (typeOf t) string))))
(assert (forall ((t Term)) (= (hasType t int) (= (typeOf t) int))))
(assert (forall ((t Term)) (= (hasType t long) (= (typeOf t) long))))
(assert (forall ((t Term)) (= (hasType t bool) (= (typeOf t) bool))))
(assert (forall ((t Term)) (= (hasType t double) (= (typeOf t) double))))
(assert (forall ((t Term)) (= (hasType t void) (= (typeOf t) void))))
(assert (forall ((t Term)) (= (hasType t raw) (= (typeOf t) raw))))
(assert (forall ((t Term)) (= (hasType t undefined) (= (typeOf t) undefined))))

(assert (forall ((t1 Term) (t2 Term)) (= (sameType t1 t2) (= (typeOf t1) (typeOf t2)))))

;; Program constraints

(push)
(declare-const $$__term_id_0 Term)
(assert (hasType $$__term_id_0 bool))
(declare-const $$__term_id_1 Term)
(assert (hasType $$__term_id_1 bool))
(declare-const $$__term_id_2 Term)
(assert (hasType $$__term_id_2 bool))
(declare-const $$__term_id_3 Term)
(assert (hasType $$__term_id_3 bool))
(declare-const $$__term_id_4 Term)
(assert (hasType $$__term_id_4 bool))
(assert (or (hasType $$__term_id_4 bool) (hasType $$__term_id_4 double) (hasType $$__term_id_4 int) (hasType $$__term_id_4 long) (hasType $$__term_id_4 string) ))

(push)
(declare-const x Term)
(declare-const $$__term_id_5 Term)
(assert (hasType $$__term_id_5 int))
(declare-const $$__term_id_6 Term)
(assert (hasType $$__term_id_6 int))
(declare-const $$__term_id_7 Term)
(assert (hasType $$__term_id_7 int))
(declare-const $$__term_id_8 Term)
(assert (hasType $$__term_id_8 int))
(declare-const $$__term_id_9 Term)
(assert (hasType $$__term_id_9 int))
(assert (sameType x $$__term_id_9))
(assert (hasType x int))
(declare-const $$__term_id_10 Term)

(check-sat)

(pop)


(push)
(declare-const x Term)
(assert (hasType x int))
(declare-const $$__term_id_11 Term)
(assert (hasType $$__term_id_11 int))
(declare-const $$__term_id_12 Term)
(assert (hasType $$__term_id_12 int))
(declare-const $$__term_id_13 Term)
(assert (hasType $$__term_id_13 int))
(declare-const $$__term_id_14 Term)
(assert (hasType $$__term_id_14 int))
(declare-const $$__term_id_15 Term)
(assert (hasType $$__term_id_15 int))
(assert (sameType x $$__term_id_15))
(assert (hasType x int))
(declare-const $$__term_id_16 Term)

(check-sat)

(pop)

(declare-const x Term)
(assert (hasType x int))
(declare-const $$__term_id_17 Term)
(assert (hasType $$__term_id_17 string))
(declare-const $$__term_id_18 Term)
(assert (hasType $$__term_id_18 string))
(declare-const $$__term_id_19 Term)
(assert (hasType $$__term_id_19 string))
(declare-const $$__term_id_20 Term)
(assert (hasType $$__term_id_20 string))
(declare-const $$__term_id_21 Term)
(assert (hasType $$__term_id_21 string))
(assert (sameType x $$__term_id_21))
(assert (hasType x string))
(declare-const $$__term_id_22 Term)

(check-sat)

(pop)


(check-sat)
