  //端末がモバイルか
  var _ua = (function(u){
    var mobile = {
              0: (u.indexOf("windows") != -1 && u.indexOf("phone") != -1)
              || u.indexOf("iphone") != -1
              || u.indexOf("ipod") != -1
              || (u.indexOf("android") != -1 && u.indexOf("mobile") != -1)
              || (u.indexOf("firefox") != -1 && u.indexOf("mobile") != -1)
              || u.indexOf("blackberry") != -1,
              iPhone: (u.indexOf("iphone") != -1),
              Android: (u.indexOf("android") != -1 && u.indexOf("mobile") != -1)
    };
    var tablet = (u.indexOf("windows") != -1 && u.indexOf("touch") != -1)
              || u.indexOf("ipad") != -1
              || (u.indexOf("android") != -1 && u.indexOf("mobile") == -1)
              || (u.indexOf("firefox") != -1 && u.indexOf("tablet") != -1)
              || u.indexOf("kindle") != -1
              || u.indexOf("silk") != -1
              || u.indexOf("playbook") != -1;
    var pc = !mobile[0] && !tablet;
    return {
      Mobile: mobile,
      Tablet: tablet,
      PC: pc
    };
  })(window.navigator.userAgent.toLowerCase());
  //ファイルが選択されたら読み込む
  function selectReadfile(e) {
    var file = e.target.files;
    var reader = new FileReader();
    var canvas = document.getElementById('capture_image');
    //dataURL形式でファイルを読み込む
    reader.readAsDataURL(file[0]);
    //ファイルの読込が終了した時の処理
    reader.onload = function(){
      readDrawImg(reader, canvas, 0, 0);
    }
  }

  function readDrawImg(reader, canvas, x, y){
    var img = readImg(reader);
    img.onload = function(){
      var w = img.width;
      var h = img.height;
      printWidthHeight( 'src-width-height', true, w, h);
      // モバイルであればリサイズ
      if(_ua.Mobile[0]){
        var resize = resizeWidthHeight(8, w, h);
        alert("w=" + w + " : h=" + h + " : resize.w=" + resize.w + " : resize.h=" + resize.h);
        //printWidthHeight( 'dst-width-height', resize.flag, resize.w, resize.h);
        drawImgOnCav(canvas, img, x, y, resize.w, resize.h);
      } //else{
//        // モバイル以外では元サイズ
//        printWidthHeight( 'dst-width-height', false, 0, 0);
        drawImgOnCav(canvas, img, x, y, w, h);
      }
    }
  //}

  //ファイルの読込が終了した時の処理
  function readImg(reader){
    //ファイル読み取り後の処理
    var result_dataURL = reader.result;
    var img = new Image();
    img.src = result_dataURL;
    return img;
  }

  //キャンバスにImageを表示
  function drawImgOnCav(canvas, img, x, y, w, h) {
    var ctx = canvas.getContext('2d');
    canvas.width = w;
    canvas.height = h;
    ctx.drawImage(img, x, y, w, h);
  }

  // リサイズ後のwidth, heightを求める
  function resizeWidthHeight(target_length_px, w0, h0){
    //リサイズの必要がなければ元のwidth, heightを返す
    var length = Math.max(w0, h0);
    if(length <= target_length_px){
      return{
        flag: false,
        w: w0,
        h: h0
      };
    }
    //リサイズの計算
    var w1;
    var h1;
    if(w0 >= h0){
      w1 = target_length_px;
      h1 = h0 * target_length_px / w0;
    }else{
      w1 = w0 * target_length_px / h0;
      h1 = target_length_px;
    }
    return {
      flag: true,
      w: parseInt(w1),
      h: parseInt(h1)
    };
  }

  function printWidthHeight( width_height_id, flag, w, h) {
//    var wh = document.getElementById(width_height_id);
//    if(!flag){
//      wh.innerHTML = "なし";
//      return;
//    }
//    wh.innerHTML = 'width:' + w + ' height:' + h;
  }

  const start = document.getElementById('start_btn');
  if (window.matchMedia && window.matchMedia('(max-device-width: 640px)').matches) {
      document.getElementById('sp').style.display = 'block';
      document.getElementById('pc').style.display = 'none';
      start.style.display = 'none';
  } else {
     document.getElementById('sp').style.display = 'none';
     document.getElementById('pc').style.display = 'block';
     start.style.display = 'block';
  }

  const camera = document.getElementById('camera');
  camera.addEventListener('change', selectReadfile, false);
  
