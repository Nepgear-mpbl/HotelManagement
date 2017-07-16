#sql("findByName")
SELECT * FROM j_user WHERE `username` = #para(0)
#end

#sql("findById")
SELECT * FROM j_user WHERE `id` = #para(0)
#end