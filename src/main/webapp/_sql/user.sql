#sql("findByName")
SELECT * FROM j_user WHERE `username` = #para(0)
#end