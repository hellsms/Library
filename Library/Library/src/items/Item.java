package items;

public interface Item {

	public void select(String query) throws Exception;

	public void update(String query) throws Exception;

	public void execute(String query) throws Exception;
}
