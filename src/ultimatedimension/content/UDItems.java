package ultimatedimension.content;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.type.Item;
import mindustry.content.Items;

public class UDItems implements ContentList{

	public static Item iron,//铁
	radioactiveSubstances,//放射物
	steel,//钢
	iterativeModule,//迭代模块
	electricCube,//电立方
	tungstenSteel,//钨钢
	aluminium,//铝
	refactoringModule,//重构模块
	nanoAlloy,//纳米合金
	dataMatrix,//数据矩阵
	superComputingMatrix,//超算矩阵
	modelMatrix,//模型矩阵
	structureMatrix,//结构矩阵
	setMatrix;//集合矩阵

	/*"explosiveness"爆炸性
	"flammability"燃烧性，在燃烧发电机中这个与发电量成正比例
	"radioactivity"辐射性，在辐射发电机中这个与发电量成正比例
	"charge"电？也许是物品爆炸放电吧，原版就合金有
	"hardness"挖掘等级
	"cost"用作建筑材料是消耗时间倍率 时间公式：各个材料需求数量乘此倍率后相加再除以60就是所需时间
	*/

	public static final Seq<Item> udItems = new Seq<>(), udOnlyItems = new Seq<>();

	@Override
	public void load(){
		iron = new Item("iron", Color.valueOf("D8DADF")){{
			hardness = 2;
			cost = 1.3f;
		}};

		radioactiveSubstances = new Item("radioactive_substances", Color.valueOf("999C77")){{
			radioactivity = 1f;
		}};

		steel = new Item("steel", Color.valueOf("E84649")){{
			cost = 1.45f;
		}};

		iterativeModule = new Item("iterative_module", Color.valueOf("E88345")){{
			cost = 1.5f;
		}};

		electricCube = new Item("electric_cube", Color.valueOf("E75665")){{
			charge = 1f;
		}};

		tungstenSteel = new Item("tungsten_steel", Color.valueOf("666A72")){{
			charge = 0.2f;
			cost = 1.2f;
		}};

		aluminium = new Item("aluminium", Color.valueOf("F65782")){{
			cost = 1f;
			hardness = 1;
		}};

		refactoringModule = new Item("refactoring_module", Color.valueOf("F65782")){{
			cost = 1f;
		}};

		nanoAlloy = new Item("nano_alloy", Color.valueOf("F65287")){{
			cost = 1.2f;
		}};

		dataMatrix = new Item("data_matrix", Color.valueOf("F65827")){{
		}};

		superComputingMatrix = new Item("supercomputing_matrix", Color.valueOf("F65827")){{
		}};

		modelMatrix = new Item("model_matrix", Color.valueOf("F65827")){{
		}};

		structureMatrix = new Item("structure_matrix", Color.valueOf("F65827")){{
		}};

		setMatrix = new Item("set_matrix", Color.valueOf("F65827")){{
		}};

		udItems.addAll(
			Items.copper, Items.lead, Items.graphite, Items.coal, Items.titanium, Items.thorium, Items.silicon,
			Items.sand, Items.metaglass,
			iron, radioactiveSubstances,
			steel, iterativeModule, electricCube, aluminium, refactoringModule,
			nanoAlloy, dataMatrix, superComputingMatrix, modelMatrix, structureMatrix,
			setMatrix
		);

		udOnlyItems.addAll(udItems).removeAll(Items.serpuloItems);
		udOnlyItems.addAll(udItems).removeAll(Items.erekirItems);
	}
}
