//https://openbanking.readme.io/v1.0/reference#get-account-balance
var serviceDefinition=[];
console.log(serviceDefinition);
function add(service){
    serviceDefinition.push(service);
}
$('.hub-reference').each( function (i, e) {
    var service={} ;
    var baseApiUrl='http://localhost:8081';
    service.serviceUrl=$(e).find('.definition-url').text().replace('api.openbanking.ng',baseApiUrl);
    if(""!==service.serviceUrl){
        var sElement=$(e).find('h2');
        service.name=sElement.text();
        service.description=sElement.text();
        service.active=true;
        service.group=service.serviceUrl.replace(baseApiUrl,'').split("\/")[1];
        add(service);
    }
});
console.log(JSON.stringify(serviceDefinition,null,3));
