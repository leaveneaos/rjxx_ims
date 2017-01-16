/**
 *feel
 */
function checkDm(str){
	//var spdm=$("#spdm1").val();
    if(str.length>4){
        alert('ERROR，商品代码长度有误，请输入长度不超过4！');       
    }
    return false;
}
function checkNum(){
    var isNum = /^\d+(\.\d+)?$/;  
	if(!isNum.test($("#spdj1").val())){
	    alert('ERROR，单价格式有误，请输入为数字！');
        return false;
    }
}
/**
 * 格式化银行帐号 每四位后一空格
 * @param num
 * @returns
 */
function formatCTNum(num){
	var reg = /^\d{19}$/g; // 以19位数字开头，以19位数字结尾 
    return num.replace(/[^0-9]/g, '').replace(/(\d{4})(?!$)/g, '$1 ')
}