#sql("findById")
SELECT * FROM j_meal WHERE `id` = #para(0)
#end

#sql("findByName")
SELECT * FROM j_meal WHERE `mealname` = #para(0)
#end

#sql("getAll")
SELECT * FROM j_meal
#end