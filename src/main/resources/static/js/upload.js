async function uploadToServer (formObj) {

    console.log("upload to server......")
    console.log(formObj)

   $.ajax({
        method: 'post',
        url: '/upload',
        data: formObj,
        headers: {
            'Content-Type': 'multipart/form-data',
        },
       success: function (result){
           console.log(result);
       }
    });

    return response.data;
}

async function removeFileToServer(uuid, fileName){

    const response = await axios.delete(`/remove/${uuid}_${fileName}`)

    return response.data

}