package org.seda.standard;

public class StandardEventService {

  private final MemoryEventSelect ses;
  private final MemoryEventExtract see;
  private final MemoryEventTransform set;
  private final MemoryEventLoad sel;
  private final StageController stageController;

  public StandardEventService() {
    stageController = new MemoryStageController();
    ses = new MemoryEventSelect();
    see = new MemoryEventExtract();
    set = new MemoryEventTransform();
    sel = new MemoryEventLoad();

    ses.setStageController(stageController);
    see.setStageController(stageController);
    set.setStageController(stageController);
    sel.setStageController(stageController);
  }

  public StandardEventSelect selectEvent() {
    return ses;
  }

  public StandardEventExtract extractEvent() {
    return see;
  }

  public StandardEventTransform transformEvent() {
    return set;
  }

  public StandardEventLoad loadEvent() {
    return sel;
  }

  public StageController controller() {
    return stageController;
  }

}
