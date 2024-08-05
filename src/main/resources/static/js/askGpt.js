/**
 *
 */
var data;

function askGpt(check) {
  if (check.checked == false) {
    return;
  }

  let name = check.name;
  const url  = name;
  const start = name.lastIndexOf("/") + 1;
  const end = name.lastIndexOf(".");
  var num = name.substring(start, end);

  const objId = 'chat' + num;
  var elmStr = document.getElementById(objId).innerHTML;
  if (elmStr != null && elmStr != "") {
   return;
  }
  
  showModal();

  /**
   * ajax処理で、CharGPTからのメッセージ取得
   */
  $.ajax({
          url:'ChatGptServlet',
          type:'POST',
          data:{
            'url':url
          }
        })
        // Ajax通信が成功したら発動
        .done( (data) => {
          renderGptMessage(data, num);
        })
        // Ajax通信が失敗したら発動
        .fail( (jqXHR, textStatus, errorThrown) => {
          alert('Ajax通信に失敗しました。');
          console.log("jqXHR          : " + jqXHR.status); // HTTPステータスを表示
          console.log("textStatus     : " + textStatus);    // タイムアウト、パースエラーなどのエラー情報を表示
          console.log("errorThrown    : " + errorThrown.message); // 例外情報を表示
        })
        // Ajax通信が成功・失敗のどちらでも発動
        .always( (data) => {
           hideModal();
        });

}
