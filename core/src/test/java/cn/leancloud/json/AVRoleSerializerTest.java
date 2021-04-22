package cn.leancloud.json;

import cn.leancloud.*;
import cn.leancloud.LCRole;
import cn.leancloud.LCObject;
import cn.leancloud.gson.GsonWrapper;
import cn.leancloud.ops.AddOperation;
import cn.leancloud.types.LCDate;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AVRoleSerializerTest extends TestCase {

  public AVRoleSerializerTest(String name) {
    super(name);
    Configure.initializeRuntime();
  }

  public void testRoleSerializer() {
    LCACL acl = new LCACL();
    acl.setPublicReadAccess(true);
    acl.setPublicWriteAccess(false);
    acl.setRoleReadAccess("Tester", true);
    acl.setRoleWriteAccess("Tester", true);
    String aclString = JSON.toJSONString(acl);
    System.out.println("jsonString of acl:" + aclString);
    LCACL otherACL = JSON.parseObject(aclString, LCACL.class);
    System.out.println("jsonString of acl:" + aclString + ", deserializedObject: " + otherACL.toString());
    assertEquals(acl, otherACL);

    LCRole role1 = new LCRole();
    LCRole role2 = new LCRole("Developer");
    HashMap<String, Boolean> aclData = new HashMap<>();
    aclData.put("read", true);
    aclData.put("write", true);
    LCRole role3 = new LCRole("Ops", new LCACL(aclData));

    List<LCRole> roleList = new ArrayList<>(3);
    roleList.add(role1);
    roleList.add(role2);
    roleList.add(role3);

    for (LCRole role : roleList) {
      String jsonString = JSON.toJSONString(role);
      System.out.println("jsonString of role:" + jsonString);
      LCRole dup = JSON.parseObject(jsonString, LCRole.class);
      System.out.println("deserializedObject: " + dup.toJSONString());
      assertEquals(role.getName(), dup.getName());
    }
  }

  public void testAVDate() {
    LCDate date = new LCDate();
    date.setIso("2020-06-06'T'00:00:00.533'Z'");
    String dateJson = date.toJSONString();
    System.out.println("jsonString of AVDate:" + dateJson);
    LCDate other = JSON.parseObject(dateJson, LCDate.class);
    System.out.println("deserializedObject: " + other.toJSONString());
    assertEquals(other.getIso().equals(date.getIso()), true);
  }

  public void testAVObjectSerializer() {
    LCObject object = new LCObject("Student");
    object.put("name", "Automatic Tester");
    object.put("age", 18);
    object.put("grade", 9);
    String objectString = object.toJSONString();
    System.out.println("objectJSONString is: " + objectString);
  }

  public void testAVObjectDeserialize() {
    String text = "{\"@type\":\"cn.leancloud.AVObject\",\"className\":\"Student\",\"version\":5,\"serverData\":{\"address\":\"Beijing City\",\"@type\":\"java.util.HashMap\",\"age\":5}}";
    LCObject object = GsonWrapper.parseObject(text, LCObject.class);
    System.out.println(object.toJSONString());
  }

  public void testAVUserSerializer() {

  }

  public void testSubClassSerializer() {
    ;
  }

  public void testBaseOperatorSerializer() {
    AddOperation op = new AddOperation("age", 5);
    System.out.println("Operator jsonString is: " + GsonWrapper.getGsonInstance().toJson(op));
  }
}
