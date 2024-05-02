async function uploadToServer (formObj) {

    console.log("upload to server......");
    console.log(formObj);
    var response;
  /*  const response = await axios({
        method: 'post',
        url: '/upload',
        data: formObj,
        processData:false,
        contentType:false,
        headers: {
            'Content-Type': 'multipart/form-data',
        },
    });*/

    $.ajax({
        type: 'post',
        url: '/upload',
        data: formObj,
        processData:false,
        contentType:false,
        success:function (result){
            console.log("result값");
            console.log(result);

            response = result; //uploadDTO List
        }

    })
    console.log("respose에영");
    console.log(response[0]);

    return response;
}

async function removeFileToServer(uuid, fileName){

    const response = await axios.delete(`/remove/${uuid}_${fileName}`)

    return response.data

}