// Important stuff: the endpoint is: "http://hostip:hostport/thriftservicename"
// where the thrift service name is defined in your .thrift
var settings= {
    "host": "192.168.99.100",
    "port": "8886",
    "service" : "PuzzleMasterService"
};

var transport = new Thrift.Transport("http://" + settings.host + ":" + settings.port + "/" + settings.service);
var protocol = new Thrift.TJSONProtocol(transport);
var client = new PuzzleMasterServiceClient(protocol);

