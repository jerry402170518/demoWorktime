$(function () {
    

    //提交
//    $("#submitBtn").on('click', function () {
//        swal({
//            title: "確定要繳交工時嗎?",
//            icon: "warning",
//            buttons: ["取消", "提交"],
//            dangerMode: true,
//        })
//            .then((willDelete) => {
//                if (willDelete) {
//                    swal("工時已繳交成功", {
//                        icon: "success",
//                    }).then((value) => { window.location = 'empWriteWorktime.html' });
//                }
//            });
//    })

    var totalTr = 0;
    //新增專案
    console.log(totalTr)
    $("#addProjectBtn").on('click', function () {
        var $lastRow = $("[id$=project] tr:last").prev();
        var $newRow = $lastRow.clone(); //clone it
        $newRow.find('input').val('').end();
        $newRow.find('textarea').val('').end();
        $lastRow.after($newRow); //add in the new row at the end

        totalTr = $lastRow.siblings().length;
        console.log(totalTr)
    })
     
    
})

//$(document).on('click', '.delBtn',function () {
//    let totalTr = $("[id$=project] tr:last").siblings().length;
//    console.log('totalTr', totalTr);
//    if (totalTr > 1) {
//        $(this).parent().parent().remove()
//    }
//})