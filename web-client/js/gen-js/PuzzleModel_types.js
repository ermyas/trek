//
// Autogenerated by Thrift Compiler (0.9.3)
//
// DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
//


PuzzleSite = function(args) {
  this.id = null;
  this.name = null;
  this.message = null;
  this.clue = null;
  this.site = null;
  if (args) {
    if (args.id !== undefined && args.id !== null) {
      this.id = args.id;
    }
    if (args.name !== undefined && args.name !== null) {
      this.name = args.name;
    }
    if (args.message !== undefined && args.message !== null) {
      this.message = args.message;
    }
    if (args.clue !== undefined && args.clue !== null) {
      this.clue = args.clue;
    }
    if (args.site !== undefined && args.site !== null) {
      this.site = new Site(args.site);
    }
  }
};
PuzzleSite.prototype = {};
PuzzleSite.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRING) {
        this.id = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.STRING) {
        this.name = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 3:
      if (ftype == Thrift.Type.STRING) {
        this.message = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 4:
      if (ftype == Thrift.Type.STRING) {
        this.clue = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 5:
      if (ftype == Thrift.Type.STRUCT) {
        this.site = new Site();
        this.site.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

PuzzleSite.prototype.write = function(output) {
  output.writeStructBegin('PuzzleSite');
  if (this.id !== null && this.id !== undefined) {
    output.writeFieldBegin('id', Thrift.Type.STRING, 1);
    output.writeString(this.id);
    output.writeFieldEnd();
  }
  if (this.name !== null && this.name !== undefined) {
    output.writeFieldBegin('name', Thrift.Type.STRING, 2);
    output.writeString(this.name);
    output.writeFieldEnd();
  }
  if (this.message !== null && this.message !== undefined) {
    output.writeFieldBegin('message', Thrift.Type.STRING, 3);
    output.writeString(this.message);
    output.writeFieldEnd();
  }
  if (this.clue !== null && this.clue !== undefined) {
    output.writeFieldBegin('clue', Thrift.Type.STRING, 4);
    output.writeString(this.clue);
    output.writeFieldEnd();
  }
  if (this.site !== null && this.site !== undefined) {
    output.writeFieldBegin('site', Thrift.Type.STRUCT, 5);
    this.site.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

Puzzle = function(args) {
  this.id = null;
  this.trail = null;
  this.startMessage = null;
  this.endMessage = null;
  this.owner = null;
  if (args) {
    if (args.id !== undefined && args.id !== null) {
      this.id = args.id;
    }
    if (args.trail !== undefined && args.trail !== null) {
      this.trail = Thrift.copyList(args.trail, [PuzzleSite]);
    }
    if (args.startMessage !== undefined && args.startMessage !== null) {
      this.startMessage = args.startMessage;
    }
    if (args.endMessage !== undefined && args.endMessage !== null) {
      this.endMessage = args.endMessage;
    }
    if (args.owner !== undefined && args.owner !== null) {
      this.owner = args.owner;
    }
  }
};
Puzzle.prototype = {};
Puzzle.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRING) {
        this.id = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.LIST) {
        var _size0 = 0;
        var _rtmp34;
        this.trail = [];
        var _etype3 = 0;
        _rtmp34 = input.readListBegin();
        _etype3 = _rtmp34.etype;
        _size0 = _rtmp34.size;
        for (var _i5 = 0; _i5 < _size0; ++_i5)
        {
          var elem6 = null;
          elem6 = new PuzzleSite();
          elem6.read(input);
          this.trail.push(elem6);
        }
        input.readListEnd();
      } else {
        input.skip(ftype);
      }
      break;
      case 3:
      if (ftype == Thrift.Type.STRING) {
        this.startMessage = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 4:
      if (ftype == Thrift.Type.STRING) {
        this.endMessage = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 5:
      if (ftype == Thrift.Type.STRING) {
        this.owner = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

Puzzle.prototype.write = function(output) {
  output.writeStructBegin('Puzzle');
  if (this.id !== null && this.id !== undefined) {
    output.writeFieldBegin('id', Thrift.Type.STRING, 1);
    output.writeString(this.id);
    output.writeFieldEnd();
  }
  if (this.trail !== null && this.trail !== undefined) {
    output.writeFieldBegin('trail', Thrift.Type.LIST, 2);
    output.writeListBegin(Thrift.Type.STRUCT, this.trail.length);
    for (var iter7 in this.trail)
    {
      if (this.trail.hasOwnProperty(iter7))
      {
        iter7 = this.trail[iter7];
        iter7.write(output);
      }
    }
    output.writeListEnd();
    output.writeFieldEnd();
  }
  if (this.startMessage !== null && this.startMessage !== undefined) {
    output.writeFieldBegin('startMessage', Thrift.Type.STRING, 3);
    output.writeString(this.startMessage);
    output.writeFieldEnd();
  }
  if (this.endMessage !== null && this.endMessage !== undefined) {
    output.writeFieldBegin('endMessage', Thrift.Type.STRING, 4);
    output.writeString(this.endMessage);
    output.writeFieldEnd();
  }
  if (this.owner !== null && this.owner !== undefined) {
    output.writeFieldBegin('owner', Thrift.Type.STRING, 5);
    output.writeString(this.owner);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

