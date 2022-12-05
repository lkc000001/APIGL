$(function(){

    // 權限視窗控制
    var Competence = $("#Competence");
    $(".member").on("click",function(){
        Competence.slideToggle()
    })
    
    var memberBlock = $(".member-block");// 排除的區塊
    $(document).click(function (e) {
        if (!memberBlock.is(e.target) && memberBlock.has(e.target).length === 0) {
        Competence.slideUp(); 
        }
    });

    // textarea輸入框 輸入內容超過高度時自動往下延展(搭配下方含式)
    //ps:也可直接於該textarea中增加 onkeyup="autogrow(this)"
    $("textarea").on("keyup",function(){
        autogrow(this);
    })
    //  textarea輸入框 輸入內容超過高度時自動往下延展
    function autogrow(textarea){
        var adjustedHeight=textarea.clientHeight;
    
        adjustedHeight=Math.max(textarea.scrollHeight,adjustedHeight);
        if (adjustedHeight>textarea.clientHeight){
            textarea.style.height=adjustedHeight+'px';
        }
    
    }

});