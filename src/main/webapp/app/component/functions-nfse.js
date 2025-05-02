function exec(o, f) {
    setTimeout(function () {o.value = f(o.value)}, 1);
}

function valdate(v) {
    if (v == null)
        return v;
    var v2 = v.replace(/\D/g, "");
    if (v2.length > 8 || v2.length <= 0) {
        v = null;
    } else {
        var dia = v2.substring(0,2);
        var mes = v2.substring(2,4);
        if (dia <= 0 || dia > 31) {
            v = null;
        }
        if (mes <= 0 || mes > 12) {
            v = null;
        }
    }
    return v;
}

function mdata(v) {
    v = v.replace(/\D/g, "");                    //Remove tudo o que não é dígito
    v = v.replace(/(\d{2})(\d)/, "$1/$2");
    v = v.replace(/(\d{2})(\d)/, "$1/$2");

    v = v.replace(/(\d{2})(\d{2})$/, "$1$2");
    if (v.toString().length > 10) {
        v = v.replace(v, v.toString().substring(0, 10));
    }
    if (v.replace(/\D/g, "").length == 8)
        v = valdate(v);

    return v;
}