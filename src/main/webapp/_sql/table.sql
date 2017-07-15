#sql("findById")
SELECT * FROM j_table WHERE `id` = #para(0)
#end

#sql("getUsed")
SELECT * FROM j_table WHERE `belong` is not null
#end

#sql("getUnused")
SELECT * FROM j_table WHERE `belong` is null
#end

#sql("getUnusedWithMinSize")
SELECT * FROM j_table WHERE `belong` is null and `seat` >= #para(0)
#end