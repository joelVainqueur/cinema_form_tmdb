$(document).ready( function () {
    var table = $('#student').DataTable({
        "sAjaxSource": "/studentPage",
        "sAjaxDataProp": "",
        "order": [[ 0, "asc" ]],
        "aoColumns": [
            { "mData": "id"},
            { "mData": "name" },
            { "mData": "percentage" },
            { "mData": "email" },
            { "mData": "phone" },
            { "mData": "branch" }
        ]
    })
});