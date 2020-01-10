package cn.leancloud.im.v2;

public class AVIMClientOpenOption {

  private boolean isReconnect = false;

  public AVIMClientOpenOption() {
  }

  /**
   * 判断是否恢复重连
   * @return flag of reconnection.
   */
  public boolean isReconnect() {
    return this.isReconnect;
  }

  /**
   * 设置恢复重连标记
   *
   * @param reconnect flag of reconnection.
   */
  public void setReconnect(boolean reconnect) {
    this.isReconnect = reconnect;
  }
}
