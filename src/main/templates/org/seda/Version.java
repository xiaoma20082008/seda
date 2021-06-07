package org.seda;

public class Version {

  /**
   * project version
   */
  public static final String VERSION = "${project.version}";
  /**
   * SCM(git) revision
   */
  public static final String SCM_REVISION = "${buildNumber}";
  /**
   * SCM branch
   */
  public static final String SCM_BRANCH = "${scmBranch}";
  /**
   * build timestamp
   */
  public static final String TIMESTAMP = "${buildTimestamp}";
}
