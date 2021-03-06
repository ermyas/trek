//
// Autogenerated by Thrift Compiler (0.9.3)
//
// DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
//


Progress = function(args) {
  this.currentStage = null;
  this.totalStages = null;
  if (args) {
    if (args.currentStage !== undefined && args.currentStage !== null) {
      this.currentStage = args.currentStage;
    }
    if (args.totalStages !== undefined && args.totalStages !== null) {
      this.totalStages = args.totalStages;
    }
  }
};
Progress.prototype = {};
Progress.prototype.read = function(input) {
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
      if (ftype == Thrift.Type.BYTE) {
        this.currentStage = input.readByte().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.BYTE) {
        this.totalStages = input.readByte().value;
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

Progress.prototype.write = function(output) {
  output.writeStructBegin('Progress');
  if (this.currentStage !== null && this.currentStage !== undefined) {
    output.writeFieldBegin('currentStage', Thrift.Type.BYTE, 1);
    output.writeByte(this.currentStage);
    output.writeFieldEnd();
  }
  if (this.totalStages !== null && this.totalStages !== undefined) {
    output.writeFieldBegin('totalStages', Thrift.Type.BYTE, 2);
    output.writeByte(this.totalStages);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

PuzzleResponse = function(args) {
  this.playerId = null;
  this.puzzleId = null;
  this.nextSiteId = null;
  this.nextSiteClue = null;
  this.message = null;
  this.correctLastGuess = null;
  this.progress = null;
  if (args) {
    if (args.playerId !== undefined && args.playerId !== null) {
      this.playerId = args.playerId;
    }
    if (args.puzzleId !== undefined && args.puzzleId !== null) {
      this.puzzleId = args.puzzleId;
    }
    if (args.nextSiteId !== undefined && args.nextSiteId !== null) {
      this.nextSiteId = args.nextSiteId;
    }
    if (args.nextSiteClue !== undefined && args.nextSiteClue !== null) {
      this.nextSiteClue = args.nextSiteClue;
    }
    if (args.message !== undefined && args.message !== null) {
      this.message = args.message;
    }
    if (args.correctLastGuess !== undefined && args.correctLastGuess !== null) {
      this.correctLastGuess = args.correctLastGuess;
    }
    if (args.progress !== undefined && args.progress !== null) {
      this.progress = new Progress(args.progress);
    }
  }
};
PuzzleResponse.prototype = {};
PuzzleResponse.prototype.read = function(input) {
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
        this.playerId = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.STRING) {
        this.puzzleId = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 3:
      if (ftype == Thrift.Type.STRING) {
        this.nextSiteId = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 4:
      if (ftype == Thrift.Type.STRING) {
        this.nextSiteClue = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 5:
      if (ftype == Thrift.Type.STRING) {
        this.message = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 6:
      if (ftype == Thrift.Type.BOOL) {
        this.correctLastGuess = input.readBool().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 7:
      if (ftype == Thrift.Type.STRUCT) {
        this.progress = new Progress();
        this.progress.read(input);
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

PuzzleResponse.prototype.write = function(output) {
  output.writeStructBegin('PuzzleResponse');
  if (this.playerId !== null && this.playerId !== undefined) {
    output.writeFieldBegin('playerId', Thrift.Type.STRING, 1);
    output.writeString(this.playerId);
    output.writeFieldEnd();
  }
  if (this.puzzleId !== null && this.puzzleId !== undefined) {
    output.writeFieldBegin('puzzleId', Thrift.Type.STRING, 2);
    output.writeString(this.puzzleId);
    output.writeFieldEnd();
  }
  if (this.nextSiteId !== null && this.nextSiteId !== undefined) {
    output.writeFieldBegin('nextSiteId', Thrift.Type.STRING, 3);
    output.writeString(this.nextSiteId);
    output.writeFieldEnd();
  }
  if (this.nextSiteClue !== null && this.nextSiteClue !== undefined) {
    output.writeFieldBegin('nextSiteClue', Thrift.Type.STRING, 4);
    output.writeString(this.nextSiteClue);
    output.writeFieldEnd();
  }
  if (this.message !== null && this.message !== undefined) {
    output.writeFieldBegin('message', Thrift.Type.STRING, 5);
    output.writeString(this.message);
    output.writeFieldEnd();
  }
  if (this.correctLastGuess !== null && this.correctLastGuess !== undefined) {
    output.writeFieldBegin('correctLastGuess', Thrift.Type.BOOL, 6);
    output.writeBool(this.correctLastGuess);
    output.writeFieldEnd();
  }
  if (this.progress !== null && this.progress !== undefined) {
    output.writeFieldBegin('progress', Thrift.Type.STRUCT, 7);
    this.progress.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

