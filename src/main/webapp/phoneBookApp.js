$(document).ready(function () {
    loadContacts();
    $('#contact-modal').on('hidden.bs.modal', function () {
        cleanFields();
    })
});

var APP_URL = "/v1/contact"

function loadContacts() {
    var query = $("#searchInput").val();
    $.ajax({
        url: APP_URL + "/search?query=" + query,
        success: function (data) {
            writeTable(data.content);
        }
    });
}

function writeTable(contacts) {
    cleanTable();
    for (i in contacts) {
        appendContact(contacts[i]);
    }
}

function appendContact(contact) {
    var tr = '<tr id="' + contact.id + '">';
    tr += '<td>';
    tr += contact.name;
    tr += '</td>';
    tr += '<td>';
    tr += contact.phone ? contact.phone : "";
    tr += '</td>';
    tr += '<td class="hidden-xs">';
    tr += contact.email ? contact.email : "";
    tr += '</td>';
    tr += '<td class="hidden-xs">';
    tr += contact.company ? contact.company : "";
    tr += '</td>';
    tr += '<td class="hidden-xs">';
    tr += contact.job_title ? contact.job_title : "";
    tr += '</td>';
    tr += '<td>';
    tr += '<div class="btn-group pull-right">';
    tr += '<a href="#" class="btn btn-primary" onclick="openModal(\'' + contact.id + '\')"><span class="glyphicon glyphicon-edit"></span></a>'
    tr += '<a href="#" class="btn btn-danger" onclick="deleteContact(\'' + contact.id + '\')"><span class="glyphicon glyphicon-trash"></span></a>'
    tr += '</div>';
    tr += '</td>';
    tr += '</tr>';
    $("#table-body").append(tr);
}

function cleanTable() {
    $("#table-body").empty();
}

function deleteContact(id) {
    if (confirm("Delete this contact?")) {
        $.ajax({
            url: APP_URL + "/" + id,
            type: 'DELETE',
            success: function (data) {
                $("#" + id).remove();
            }
        });
    }
}

function openModal(id) {
    cleanFields()
    if (id) {
        $.ajax({
            url: APP_URL + "/" + id,
            success: function (contact) {
                $("#contact-id").val(contact.id);
                $("#contact-name").val(contact.name);
                $("#contact-email").val(contact.email);
                $("#contact-phone").val(contact.phone);
                $("#contact-company").val(contact.company);
                $("#contact-job-title").val(contact.job_title);
                $("#contact-notes").val(contact.notes);
                $('#contact-modal').modal();
            }
        });
    } else {
        $('#contact-modal').modal();
    }
}

function saveOrUpdate() {
    var contact = new Object();
    contact.id = $("#contact-id").val();
    contact.name = $("#contact-name").val();
    contact.email = $("#contact-email").val();
    contact.phone = $("#contact-phone").val();
    contact.company = $("#contact-company").val();
    contact.job_title = $("#contact-job-title").val();
    contact.notes = $("#contact-notes").val();

    if(contact.name == ""){
        alert("Please inform at least the contact's name.");
        return;
    }

    $.ajax({
        url: APP_URL,
        type: contact.id ? "PUT" : "POST",
        data: JSON.stringify(contact),
        contentType: 'application/json',
        success: function (data) {
            $('#contact-modal').modal('hide');
            if (contact.id) {
                loadContacts();
            } else {
                appendContact(data);
            }
        },
        error: function(){
          alert("Please, check if you informed a valid email.");
        }
    });
}

function cleanFields() {
    $("#contact-id").val("");
    $("#contact-name").val("");
    $("#contact-email").val("");
    $("#contact-phone").val("");
    $("#contact-company").val("");
    $("#contact-job-title").val("");
    $("#contact-notes").val("");
}